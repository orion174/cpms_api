package com.cpms.api.suport.service.impl;

import static com.cpms.common.util.CommonUtil.hasFiles;
import static com.cpms.common.util.CommonUtil.parseToIntSafely;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cpms.api.auth.model.CpmsUser;
import com.cpms.api.auth.repository.CpmsUserRepository;
import com.cpms.api.code.model.ComCodeDetail;
import com.cpms.api.code.repository.ComCodeRepository;
import com.cpms.api.suport.dto.req.ReqSuportDTO;
import com.cpms.api.suport.dto.req.ReqSuportListDTO;
import com.cpms.api.suport.dto.res.ResSuportListDTO;
import com.cpms.api.suport.model.SuportFile;
import com.cpms.api.suport.model.SuportReq;
import com.cpms.api.suport.repository.SuportFileRepository;
import com.cpms.api.suport.repository.SuportReqRepository;
import com.cpms.api.suport.service.SuportService;
import com.cpms.api.user.model.CpmsCompany;
import com.cpms.api.user.model.CpmsProject;
import com.cpms.api.user.repository.CpmsCompanyRepository;
import com.cpms.api.user.repository.CpmsProjectRepository;
import com.cpms.common.helper.FileDTO;
import com.cpms.common.jwt.JwtTokenProvider;
import com.cpms.common.res.ApiRes;
import com.cpms.common.util.FileUtil;
import com.cpms.common.util.PageUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuportServiceImpl implements SuportService {

    @Value("${file.upload.path}")
    private String uploadPath;

    private final JwtTokenProvider jwtTokenProvider;

    private final SuportReqRepository suportReqRepository;

    private final SuportFileRepository suportFileRepository;

    private final ComCodeRepository comCodeRepository;

    private final CpmsUserRepository cpmsUserRepository;

    private final CpmsCompanyRepository cpmsCompanyRepository;

    private final CpmsProjectRepository cpmsProjectRepository;

    /* jwt token으로 사용자 정보를 반환한다. */
    private Integer getUserId() {
        String userIdStr = jwtTokenProvider.getClaim("userId");
        return parseToIntSafely(userIdStr);
    }

    private Integer getCompanyId() {
        String companyIdStr = jwtTokenProvider.getClaim("companyId");
        return parseToIntSafely(companyIdStr);
    }

    private String getAuthType() {
        return jwtTokenProvider.getClaim("authType");
    }

    /**
     * 유지보수 요청문의를 저장한다.
     *
     * @param reqSuportDTO
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public ResponseEntity<?> insertReqSuport(ReqSuportDTO reqSuportDTO) throws Exception {
        boolean result = true;

        CpmsCompany reqCompany =
                cpmsCompanyRepository
                        .findById(reqSuportDTO.getReqCompanyId())
                        .orElseThrow(() -> new Exception("Invalid Company ID"));

        CpmsCompany userCompany =
                cpmsCompanyRepository
                        .findById(getCompanyId())
                        .orElseThrow(() -> new Exception("Invalid User Company ID"));

        CpmsProject reqProject =
                cpmsProjectRepository
                        .findById(reqSuportDTO.getReqProjectId())
                        .orElseThrow(() -> new Exception("Invalid Project ID"));

        CpmsUser regUser =
                cpmsUserRepository
                        .findById(getUserId())
                        .orElseThrow(() -> new Exception("Invalid User ID"));

        ComCodeDetail requestCdDetail =
                comCodeRepository
                        .findByMasterCodeIdAndCodeId("10", reqSuportDTO.getRequestCd())
                        .orElseThrow(() -> new Exception("Invalid Request Code"));

        ComCodeDetail statusCdDetail =
                comCodeRepository
                        .findByMasterCodeIdAndCodeId("20", reqSuportDTO.getStatusCd())
                        .orElseThrow(() -> new Exception("Invalid Status Code"));

        SuportReq suportReq =
                new SuportReq(
                        reqCompany,
                        userCompany,
                        reqProject,
                        null,
                        requestCdDetail,
                        statusCdDetail,
                        reqSuportDTO.getReqDate(),
                        reqSuportDTO.getSuportTitle(),
                        reqSuportDTO.getSuportEditor(),
                        regUser,
                        null);

        // 유지보수 요청 저장
        suportReqRepository.save(suportReq);

        // 파일 업로드 처리
        if (hasFiles(reqSuportDTO.getSuportFile())) {
            String fileType = "RES";
            boolean fileResult =
                    suportFileUpload(reqSuportDTO.getSuportFile(), suportReq, regUser, fileType);

            if (!fileResult) {
                result = false;
            }
        }

        return new ResponseEntity<>(new ApiRes(result), HttpStatus.OK);
    }

    /**
     * 유지보수 리스트를 조회한다.
     *
     * @param reqSuportListDTO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> selectSuportList(ReqSuportListDTO reqSuportListDTO) {
        // 날짜형 데이터 NULL 처리
        if (reqSuportListDTO.getSchStartDt() == null) {
            reqSuportListDTO.setSchStartDt("");
        }
        if (reqSuportListDTO.getSchEndDt() == null) {
            reqSuportListDTO.setSchEndDt("");
        }

        Pageable pageable =
                PageUtil.createPageable(
                        reqSuportListDTO.getPageNo(), reqSuportListDTO.getPageSize());

        Page<ResSuportListDTO.SuportList> suportListPage =
                suportReqRepository.findSuportList(reqSuportListDTO, pageable);

        ResSuportListDTO result =
                ResSuportListDTO.builder()
                        .suportCnt((int) suportListPage.getTotalElements())
                        .suportList(suportListPage.getContent())
                        .build();

        return new ResponseEntity<>(new ApiRes(result), HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> selectSuportDetail(ReqSuportDTO reqSuportDTO) {
        boolean result = true;

        Integer suportReqId = reqSuportDTO.getSuportReqId();

        if (suportReqId != 0 && suportReqId != null) {}

        return new ResponseEntity<>(new ApiRes(result), HttpStatus.OK);
    }

    /**
     * 유지보수 요청문의 첨부파일을 저장한다.
     *
     * @param files
     * @param suportReq
     * @return
     * @throws Exception
     */
    private boolean suportFileUpload(
            MultipartFile[] files, SuportReq suportReq, CpmsUser regUser, String fileType)
            throws Exception {
        // 첨부파일 개수 만큼 반복
        for (MultipartFile file : files) {
            // 오늘 날짜 생성
            Calendar dateTime = Calendar.getInstance();
            String today = (new SimpleDateFormat("yyyyMMdd")).format(dateTime.getTime());

            // 경로에 파일을 저장한다.
            FileDTO fileDTO = FileUtil.fileUpload(file, uploadPath + "/" + today);

            SuportFile suportFile =
                    new SuportFile(
                            suportReq,
                            fileType,
                            fileDTO.getFilePath(),
                            fileDTO.getFileNm(),
                            fileDTO.getFileOgNm(),
                            fileDTO.getFileExt(),
                            fileDTO.getFileSize(),
                            regUser);

            suportFileRepository.save(suportFile);
        }

        return true;
    }
}
