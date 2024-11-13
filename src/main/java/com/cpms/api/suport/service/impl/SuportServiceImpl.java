package com.cpms.api.suport.service.impl;

import static com.cpms.common.util.CommonUtil.hasFiles;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cpms.api.suport.dto.req.ReqSuportDTO;
import com.cpms.api.suport.model.SuportFile;
import com.cpms.api.suport.model.SuportReq;
import com.cpms.api.suport.repository.SuportFileRepository;
import com.cpms.api.suport.repository.SuportReqRepository;
import com.cpms.api.suport.service.SuportService;
import com.cpms.common.jwt.JwtTokenProvider;
import com.cpms.common.res.ApiRes;
import com.cpms.common.util.FileDTO;
import com.cpms.common.util.FileUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuportServiceImpl implements SuportService {

    @Value("${file.upload.path}")
    private String uploadPath;

    private final JwtTokenProvider jwtTokenProvider;

    private final SuportReqRepository suportReqRepository;

    private final SuportFileRepository suportFileRepository;

    private Integer parseToIntSafely(String value) {
        try {
            return (int) Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("변환실패: " + value, e);
        }
    }

    private Integer getUserId() {
        String userIdStr = jwtTokenProvider.getClaim("userId");
        return parseToIntSafely(userIdStr);
    }

    private Integer getCompanyId() {
        String companyIdStr = jwtTokenProvider.getClaim("companyId");
        return parseToIntSafely(companyIdStr);
    }

    private boolean suportFileUpload(MultipartFile[] files, SuportReq suportReq) throws Exception {
        for (MultipartFile file : files) {
            Calendar dateTime = Calendar.getInstance();
            String today = (new SimpleDateFormat("yyyyMMdd")).format(dateTime.getTime());
            FileDTO fileDTO = FileUtil.fileUpload(file, uploadPath + "/" + today);

            SuportFile suportFile =
                    new SuportFile(
                            fileDTO.getFilePath(),
                            fileDTO.getFileNm(),
                            fileDTO.getFileOgNm(),
                            fileDTO.getFileExt(),
                            fileDTO.getFileSize(),
                            getUserId(),
                            suportReq);
            // 첨부파일 저장
            suportFileRepository.save(suportFile);
        }

        return true;
    }

    @Override
    @Transactional
    public ResponseEntity<?> insertReqSuport(ReqSuportDTO reqSuportDTO) throws Exception {
        boolean result = true;
        // 유지보수 요청 엔티티 생성
        SuportReq suportReq =
                new SuportReq(
                        reqSuportDTO.getReqCompanyId(),
                        getCompanyId(),
                        reqSuportDTO.getReqProjectId(),
                        0, // 처리 담당자 초기화 값
                        reqSuportDTO.getRequestCd(),
                        reqSuportDTO.getStatusCd(),
                        reqSuportDTO.getReqDate(),
                        reqSuportDTO.getSuportTitle(),
                        reqSuportDTO.getSuportEditor(),
                        getUserId()); // 작성자

        // 유지보수 요청 저장
        suportReqRepository.save(suportReq);

        if (hasFiles(reqSuportDTO.getSuportFile())) {
            boolean fileResult = suportFileUpload(reqSuportDTO.getSuportFile(), suportReq);

            if (!fileResult) {
                result = false;
            }
        }

        return new ResponseEntity<>(new ApiRes(result), HttpStatus.OK);
    }
}
