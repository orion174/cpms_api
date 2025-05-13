package com.cpms.api.support.service.impl;

import static com.cpms.common.util.CommonUtil.hasFiles;
import static com.cpms.common.util.CommonUtil.parseToIntSafely;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cpms.api.auth.model.CpmsUser;
import com.cpms.api.auth.repository.CpmsUserRepository;
import com.cpms.api.code.model.CommonCode;
import com.cpms.api.code.repository.CommonCodeRepository;
import com.cpms.api.setting.model.CpmsCompany;
import com.cpms.api.setting.model.CpmsProject;
import com.cpms.api.setting.repository.CpmsCompanyRepository;
import com.cpms.api.setting.repository.CpmsProjectRepository;
import com.cpms.api.support.dto.request.ReqSupportDTO;
import com.cpms.api.support.dto.request.ReqSupportListDTO;
import com.cpms.api.support.dto.request.ReqSupportResponseDTO;
import com.cpms.api.support.dto.response.ResSupportDetailDTO;
import com.cpms.api.support.dto.response.ResSupportFileDTO;
import com.cpms.api.support.dto.response.ResSupportListDTO;
import com.cpms.api.support.model.SupportFile;
import com.cpms.api.support.model.SupportRequest;
import com.cpms.api.support.model.SupportResponse;
import com.cpms.api.support.repository.SupportFileRepository;
import com.cpms.api.support.repository.SupportRequestRepository;
import com.cpms.api.support.repository.SupportResponseRepository;
import com.cpms.api.support.service.SupportService;
import com.cpms.common.exception.CustomException;
import com.cpms.common.helper.FileDTO;
import com.cpms.common.helper.YesNo;
import com.cpms.common.jwt.JwtTokenProvider;
import com.cpms.common.response.ErrorCode;
import com.cpms.common.util.FileUtil;
import com.cpms.common.util.PageUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SuportServiceImpl implements SupportService {

    @Value("${support.file.upload.path}")
    private String uploadPath;

    private final JwtTokenProvider jwtTokenProvider;

    private final SupportRequestRepository supportRequestRepository;

    private final SupportResponseRepository supportResponseRepository;

    private final SupportFileRepository supportFileRepository;

    private final CommonCodeRepository commonCodeRepository;

    private final CpmsUserRepository cpmsUserRepository;

    private final CpmsCompanyRepository cpmsCompanyRepository;

    private final CpmsProjectRepository cpmsProjectRepository;

    /* TODO 해당 부분 공통 코드로 관리되게 이관 (임시) 문의 첨부파일은 'REQ' 응답 첨부파일은 'RES' */
    private static final String FILE_TYPE_REQ = "REQ";

    private static final String FILE_TYPE_RES = "RES";

    /**
     * 현재 로그인한 사용자 ID를 가져온다.
     *
     * @return
     */
    private Integer getUserId() {
        String userIdStr = jwtTokenProvider.getClaim("userId");
        return parseToIntSafely(userIdStr);
    }

    /**
     * 현재 로그인한 사용자의 업체 ID를 가져온다.
     *
     * @return
     */
    private Integer getCompanyId() {
        String companyIdStr = jwtTokenProvider.getClaim("companyId");
        return parseToIntSafely(companyIdStr);
    }

    /**
     * 현재 로그인한 사용자의 권한 등급을 가져온다.
     *
     * @return
     */
    private String getAuthType() {
        return jwtTokenProvider.getClaim("authType");
    }

    /**
     * 문의를 신규 등록한다.
     *
     * @param reqSupportDTO
     * @return
     */
    @Override
    @Transactional
    public boolean insertSupportRequest(ReqSupportDTO reqSupportDTO) {
        CpmsCompany requestCompany =
                cpmsCompanyRepository
                        .findById(reqSupportDTO.getRequestCompanyId())
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        CpmsCompany userCompany =
                cpmsCompanyRepository
                        .findById(getCompanyId())
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        CpmsProject requestProject =
                cpmsProjectRepository
                        .findById(reqSupportDTO.getRequestProjectId())
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        CommonCode requestCd =
                commonCodeRepository
                        .findById(reqSupportDTO.getRequestCd())
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        CommonCode statusCd =
                commonCodeRepository
                        .findById(reqSupportDTO.getStatusCd())
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        SupportRequest supportRequest =
                new SupportRequest(
                        requestCompany,
                        userCompany,
                        requestProject,
                        null,
                        requestCd,
                        statusCd,
                        reqSupportDTO.getRequestDate(),
                        null,
                        reqSupportDTO.getSupportTitle(),
                        reqSupportDTO.getSupportEditor(),
                        getUserId());

        // 유지보수 요청 저장
        supportRequestRepository.save(supportRequest);

        // 파일 업로드 처리
        if (hasFiles(reqSupportDTO.getSupportFile())) {
            String fileType = FILE_TYPE_REQ;
            supportFileUpload(reqSupportDTO.getSupportFile(), supportRequest, fileType);
        }

        return true;
    }

    /**
     * 문의 목록을 조회한다.
     *
     * @param reqSupportListDTO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResSupportListDTO selectSupportList(ReqSupportListDTO reqSupportListDTO) {
        // 날짜형 데이터 NULL 처리
        reqSupportListDTO.setSearchStartDt(
                Optional.ofNullable(reqSupportListDTO.getSearchStartDt()).orElse(""));

        reqSupportListDTO.setSearchEndDt(
                Optional.ofNullable(reqSupportListDTO.getSearchEndDt()).orElse(""));

        // USER 권한은 자신이 속한 업체의 요청 데이터만 조회가 가능하다.
        Optional.ofNullable(getAuthType())
                .filter(authType -> "USER".equals(authType))
                .ifPresent(authType -> reqSupportListDTO.setSearchCompanyId(getCompanyId()));

        Pageable pageable =
                PageUtil.createPageable(
                        reqSupportListDTO.getPageNo(), reqSupportListDTO.getPageSize());

        Page<ResSupportListDTO.SupportList> supportListPage =
                supportRequestRepository.findSupportList(reqSupportListDTO, pageable);

        ResSupportListDTO result =
                ResSupportListDTO.builder()
                        .totalCnt((int) supportListPage.getTotalElements())
                        .supportList(supportListPage.getContent())
                        .authType(getAuthType())
                        .build();

        return result;
    }

    /**
     * 문의 상세 조회
     *
     * @param reqSupportDTO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResSupportDetailDTO selectSupportDetail(ReqSupportDTO reqSupportDTO) {
        Integer supportRequestId = reqSupportDTO.getSupportRequestId();

        if (supportRequestId == null || supportRequestId == 0) {
            throw new CustomException(ErrorCode.INVALID_REQUEST_ID);
        }

        ResSupportDetailDTO result = supportRequestRepository.findSupportDetail(supportRequestId);

        result.setAuthType(getAuthType());

        // USER 권한은 자신이 속한 업체의 요청 데이터만 조회가 가능하다.
        if ("USER".equals(getAuthType())) {
            Integer companyId = getCompanyId();
            Integer userCompanyId = result.getUserCompanyId();

            if (!companyId.equals(userCompanyId)) {
                throw new CustomException(ErrorCode.NO_AUTHORITY);
            }
        }

        return result;
    }

    /**
     * 응답을 등록 한다.
     *
     * @param reqSupportResponseDTO
     * @return
     */
    @Override
    @Transactional
    public boolean insertSupportResponse(ReqSupportResponseDTO reqSupportResponseDTO) {
        SupportRequest supportRequest =
                supportRequestRepository
                        .findById(reqSupportResponseDTO.getSupportRequestId())
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        CommonCode statusCd =
                commonCodeRepository
                        .findById(reqSupportResponseDTO.getResponseStatusCd())
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        CpmsUser responseUserId =
                cpmsUserRepository
                        .findById(getUserId())
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        // 처리 상태 업데이트
        supportRequest.updateStatusCd(statusCd);

        // 처리 담당자 업데이트
        supportRequest.updateResponseUser(responseUserId);

        SupportResponse supportResponse =
                new SupportResponse(
                        supportRequest,
                        reqSupportResponseDTO.getResponseTitle(),
                        reqSupportResponseDTO.getResponseEditor(),
                        getUserId());

        // 응답 저장
        supportResponseRepository.save(supportResponse);

        // 응답 첨부파일 저장
        if (hasFiles(reqSupportResponseDTO.getResponseFile())) {
            String fileType = FILE_TYPE_RES;
            supportFileUpload(reqSupportResponseDTO.getResponseFile(), supportRequest, fileType);
        }

        return true;
    }

    /**
     * 응답 답변을 수정한다.
     *
     * @param reqSupportResponseDTO
     * @return
     */
    @Override
    @Transactional
    public boolean updateSupportResponse(ReqSupportResponseDTO reqSupportResponseDTO) {
        Integer userId = getUserId();

        // 일반 사용자는 답변을 수정 할 수 없다.
        if ("USER".equals(getAuthType())) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        CommonCode statusCd =
                commonCodeRepository
                        .findById(reqSupportResponseDTO.getResponseStatusCd())
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        SupportRequest supportRequest =
                supportRequestRepository
                        .findById(reqSupportResponseDTO.getSupportRequestId())
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        // 답변 문의 본문 처리상태 업데이트
        supportRequest.updateStatusCd(statusCd);

        // 답변 수정
        supportResponseRepository
                .findById(reqSupportResponseDTO.getSupportResponseId())
                .ifPresentOrElse(
                        supportResponse -> {
                            supportResponse.updateResponse(
                                    reqSupportResponseDTO.getResponseTitle(),
                                    reqSupportResponseDTO.getResponseEditor(),
                                    userId);

                            // 파일이 있는 경우 업로드 처리
                            if (hasFiles(reqSupportResponseDTO.getResponseFile())) {
                                String fileType = FILE_TYPE_RES;

                                supportFileUpload(
                                        reqSupportResponseDTO.getResponseFile(),
                                        supportRequest,
                                        fileType);
                            }
                        },
                        () -> {
                            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
                        });

        return true;
    }

    /**
     * 답변을 삭제한다.
     *
     * @param reqSupportDTO
     * @return
     */
    @Override
    @Transactional
    public boolean deleteSupportResponse(ReqSupportDTO reqSupportDTO) {
        Integer userId = getUserId();
        Integer supportRequestId = reqSupportDTO.getSupportRequestId();

        // 일반 사용자는 답변을 수정 할 수 없다.
        if ("USER".equals(getAuthType())) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        Optional<SupportResponse> supportResponseOpt =
                supportResponseRepository.findBySupportRequest_SupportRequestIdAndDelYn(
                        supportRequestId, YesNo.N);

        supportResponseOpt.ifPresentOrElse(
                supportResponse -> {
                    // 답변 삭제
                    supportResponse.deleteResponse(YesNo.Y, userId);

                    // 답변 첨부파일 삭제
                    List<SupportFile> fileList =
                            supportFileRepository
                                    .findBySupportRequest_SupportRequestIdAndFileTypeAndDelYn(
                                            supportRequestId, FILE_TYPE_RES, YesNo.N);

                    fileList.forEach(file -> file.deleteFile(YesNo.Y, userId));
                },
                () -> {
                    throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
                });

        return true;
    }

    /**
     * 'ADMIN' 권한 사용자가 문의글을 조회하면 자동으로 처리상태가 업데이트 된다.
     *
     * @param reqSupportDTO
     * @return
     */
    @Override
    @Transactional
    public boolean updateSupportStatus(ReqSupportDTO reqSupportDTO) {
        // 'ADMIN' 권한일때만 상태코드를 변경한다.
        if ("ADMIN".equals(getAuthType())) {
            SupportRequest supportRequest =
                    supportRequestRepository
                            .findById(reqSupportDTO.getSupportRequestId())
                            .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

            CommonCode statusCd =
                    commonCodeRepository
                            .findById(reqSupportDTO.getStatusCd())
                            .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

            supportRequest.updateStatusCd(statusCd);

            supportRequestRepository.save(supportRequest);
        }

        return true;
    }

    /**
     * 처리 담당자 업데이트
     *
     * @param reqSupportDTO
     * @return
     */
    @Override
    @Transactional
    public boolean updateResponseUserInfo(ReqSupportDTO reqSupportDTO) {
        SupportRequest supportRequest =
                supportRequestRepository
                        .findById(reqSupportDTO.getSupportRequestId())
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        CpmsUser responseUser =
                cpmsUserRepository
                        .findById(reqSupportDTO.getUserId())
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        supportRequest.updateResponseUser(responseUser);

        supportRequestRepository.save(supportRequest);

        return true;
    }

    /**
     * 첨부파일 다운로드
     *
     * @param supportFileId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResSupportFileDTO fileDownload(int supportFileId) {
        SupportFile file =
                supportFileRepository
                        .findById(supportFileId)
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        return new ResSupportFileDTO(file.getFilePath(), file.getFileNm());
    }

    /**
     * 첨부파일 삭제
     *
     * @param supportFileId
     */
    @Override
    @Transactional
    public void fileDelete(int supportFileId) {
        // 일반 사용자는 첨부파일을 삭제할 수 없다.
        if ("USER".equals(getAuthType())) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        SupportFile file =
                supportFileRepository
                        .findById(supportFileId)
                        .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        file.deleteFile(YesNo.Y, getUserId());
    }

    /**
     * 문의(응답) 파일 업로드 공통
     *
     * @param files
     * @param supportRequest
     * @param fileType
     */
    @Transactional
    private void supportFileUpload(
            MultipartFile[] files, SupportRequest supportRequest, String fileType) {

        for (MultipartFile file : files) {
            try {
                // 오늘 날짜 생성
                String today =
                        new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

                // 파일 업로드
                FileDTO fileDTO = FileUtil.fileUpload(file, uploadPath + "/" + today);

                // DB 저장
                SupportFile supportFile =
                        new SupportFile(
                                supportRequest,
                                fileType,
                                fileDTO.getFilePath(),
                                fileDTO.getFileNm(),
                                fileDTO.getFileOgNm(),
                                fileDTO.getFileExt(),
                                fileDTO.getFileSize(),
                                getUserId());

                supportFileRepository.save(supportFile);

            } catch (Exception e) {
                throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
            }
        }
    }
}
