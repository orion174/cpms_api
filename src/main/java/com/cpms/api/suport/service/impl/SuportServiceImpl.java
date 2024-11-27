package com.cpms.api.suport.service.impl;

import static com.cpms.common.util.CommonUtil.hasFiles;
import static com.cpms.common.util.CommonUtil.parseToIntSafely;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

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
import com.cpms.api.code.model.ComCode;
import com.cpms.api.code.repository.ComCodeRepository;
import com.cpms.api.suport.dto.req.ReqSuportDTO;
import com.cpms.api.suport.dto.req.ReqSuportListDTO;
import com.cpms.api.suport.dto.req.ReqSuportResDTO;
import com.cpms.api.suport.dto.res.ResSuportDetailDTO;
import com.cpms.api.suport.dto.res.ResSuportListDTO;
import com.cpms.api.suport.model.SuportFile;
import com.cpms.api.suport.model.SuportReq;
import com.cpms.api.suport.model.SuportRes;
import com.cpms.api.suport.repository.SuportFileRepository;
import com.cpms.api.suport.repository.SuportReqRepository;
import com.cpms.api.suport.repository.SuportResRepository;
import com.cpms.api.suport.service.SuportService;
import com.cpms.api.user.model.CpmsCompany;
import com.cpms.api.user.model.CpmsProject;
import com.cpms.api.user.repository.CpmsCompanyRepository;
import com.cpms.api.user.repository.CpmsProjectRepository;
import com.cpms.common.helper.FileDTO;
import com.cpms.common.helper.YesNo;
import com.cpms.common.jwt.JwtTokenProvider;
import com.cpms.common.res.ApiRes;
import com.cpms.common.util.FileUtil;
import com.cpms.common.util.PageUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuportServiceImpl implements SuportService {

    @Value("${suport.file.upload.path}")
    private String uploadPath;

    private final JwtTokenProvider jwtTokenProvider;

    private final SuportReqRepository suportReqRepository;

    private final SuportResRepository suportResRepository;

    private final SuportFileRepository suportFileRepository;

    private final ComCodeRepository comCodeRepository;

    private final CpmsUserRepository cpmsUserRepository;

    private final CpmsCompanyRepository cpmsCompanyRepository;

    private final CpmsProjectRepository cpmsProjectRepository;

    private static final String FILE_TYPE_REQ = "REQ"; // 문의 첨부파일은 'REQ'

    private static final String FILE_TYPE_RES = "RES"; // 문의 첨부파일은 'RES'

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
                        .orElseThrow(() -> new Exception("유효하지 않는 회사 ID"));

        CpmsCompany userCompany =
                cpmsCompanyRepository
                        .findById(getCompanyId())
                        .orElseThrow(() -> new Exception("유효하지 않는 회사 ID"));

        CpmsProject reqProject =
                cpmsProjectRepository
                        .findById(reqSuportDTO.getReqProjectId())
                        .orElseThrow(() -> new Exception("유효하지 않는 프로젝트 ID"));

        ComCode requestCd =
                comCodeRepository
                        .findById(reqSuportDTO.getRequestCd())
                        .orElseThrow(() -> new Exception("유효하지 않는 코드"));

        ComCode statusCd =
                comCodeRepository
                        .findById(reqSuportDTO.getStatusCd())
                        .orElseThrow(() -> new Exception("유효하지 않는 코드"));

        CpmsUser regUser =
                cpmsUserRepository
                        .findById(getUserId())
                        .orElseThrow(() -> new Exception("유효하지 않는 사용자 ID"));

        SuportReq suportReq =
                new SuportReq(
                        reqCompany,
                        userCompany,
                        reqProject,
                        null,
                        requestCd,
                        statusCd,
                        reqSuportDTO.getReqDate(),
                        reqSuportDTO.getSuportTitle(),
                        reqSuportDTO.getSuportEditor(),
                        regUser);

        // 유지보수 요청 저장
        suportReqRepository.save(suportReq);

        // 파일 업로드 처리
        if (hasFiles(reqSuportDTO.getSuportFile())) {
            String fileType = FILE_TYPE_REQ;

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

    /**
     * 유지보수 문의 상세 조회
     *
     * @param reqSuportDTO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> selectSuportDetail(ReqSuportDTO reqSuportDTO) {
        ResSuportDetailDTO result = new ResSuportDetailDTO();

        // 유지보수 요청 글 키
        Integer suportReqId = reqSuportDTO.getSuportReqId();

        if (suportReqId != 0 && suportReqId != null) {
            result = suportReqRepository.findSuportDetail(suportReqId);
        }

        return new ResponseEntity<>(new ApiRes(result), HttpStatus.OK);
    }

    /**
     * 문의의 대한 답변을 저장한다.
     *
     * @param reqSuportResDTO
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public ResponseEntity<?> insertResSuport(ReqSuportResDTO reqSuportResDTO) throws Exception {
        boolean result = true;

        SuportReq suportReq =
                suportReqRepository
                        .findById(reqSuportResDTO.getSuportReqId())
                        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Suport Req ID"));

        ComCode statusCd =
                comCodeRepository
                        .findById(reqSuportResDTO.getResStatusCd())
                        .orElseThrow(() -> new Exception("유효하지 않는 코드"));

        CpmsUser userId =
                cpmsUserRepository
                        .findById(getUserId())
                        .orElseThrow(() -> new Exception("유효하지 않는 사용자 ID"));

        // 프로젝트 유지보수 문의 본문, 처리상태, 처리담당자 업데이트
        suportReq.updateStatusCd(statusCd, getUserId());
        suportReq.updateResUser(userId, getUserId());

        SuportRes suportRes =
                new SuportRes(
                        suportReq,
                        reqSuportResDTO.getResTitle(),
                        reqSuportResDTO.getResEditor(),
                        userId);

        // 유지보수 요청 저장
        suportResRepository.save(suportRes);

        // 답변 첨부파일 저장
        if (hasFiles(reqSuportResDTO.getResFile())) {
            String fileType = FILE_TYPE_RES;

            boolean fileResult =
                    suportFileUpload(reqSuportResDTO.getResFile(), suportReq, userId, fileType);

            if (!fileResult) {
                result = false;
            }
        }

        return new ResponseEntity<>(new ApiRes(result), HttpStatus.OK);
    }

    /**
     * 처리내역 답변을 삭제한다.
     *
     * @param reqSuportDTO
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<?> deleteResSuport(ReqSuportDTO reqSuportDTO) {
        Integer suportReqId = reqSuportDTO.getSuportReqId();
        Integer userId = getUserId();

        Optional<SuportRes> suportResOpt =
                suportResRepository.findBySuportReq_SuportReqIdAndDelYn(suportReqId, YesNo.N);

        suportResOpt.ifPresentOrElse(
                suportRes -> {
                    // 답변 삭제
                    suportRes.deleteRes(YesNo.Y, userId);
                    suportResRepository.save(suportRes);

                    // 답변 첨부파일 삭제
                    List<SuportFile> fileList =
                            suportFileRepository.findBySuportReq_SuportReqIdAndFileTypeAndDelYn(
                                    suportReqId, FILE_TYPE_RES, YesNo.N);
                    fileList.forEach(file -> file.deleteFile(YesNo.Y, userId));
                },
                () -> {
                    throw new EntityNotFoundException("Suport Res ID의 데이터가 존재하지않습니다.");
                });

        return new ResponseEntity<>(new ApiRes(true), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateStatus(ReqSuportDTO reqSuportDTO) {
        boolean result = false;

        SuportReq suportReq =
                suportReqRepository
                        .findById(reqSuportDTO.getSuportReqId())
                        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Suport Req ID"));

        ComCode statusCd =
                comCodeRepository
                        .findById(reqSuportDTO.getStatusCd())
                        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 코드"));

        suportReq.updateStatusCd(statusCd, getUserId());

        suportReqRepository.save(suportReq);

        result = true;

        return new ResponseEntity<>(new ApiRes(result), HttpStatus.OK);
    }

    /**
     * 프로젝트 문의 처리 담당자 업데이트
     *
     * @param reqSuportDTO
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<?> updateUser(ReqSuportDTO reqSuportDTO) {
        boolean result = false;

        SuportReq suportReq =
                suportReqRepository
                        .findById(reqSuportDTO.getSuportReqId())
                        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 Suport Req ID"));

        CpmsUser resUser =
                cpmsUserRepository
                        .findById(reqSuportDTO.getUserId())
                        .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID"));

        suportReq.updateResUser(resUser, getUserId());

        suportReqRepository.save(suportReq);

        result = true;

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
    @Transactional
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

    /**
     * 첨부파일 다운로드
     *
     * @param suportFileId
     * @return
     */
    @Override
    public ResponseEntity<?> fileDownload(int suportFileId) {
        SuportFile file =
                suportFileRepository
                        .findById(suportFileId)
                        .orElseThrow(() -> new EntityNotFoundException("유효하지 않는 파일 ID"));

        return FileUtil.fileDownload(file.getFilePath(), file.getFileNm());
    }
}
