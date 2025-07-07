package com.cpms.api.support.service.impl;

import static com.cpms.common.util.CommonUtil.hasFiles;

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
import com.cpms.common.helper.EntityFinder;
import com.cpms.common.helper.FileDTO;
import com.cpms.common.helper.FileType;
import com.cpms.common.helper.YesNo;
import com.cpms.common.response.ErrorCode;
import com.cpms.common.util.FileUtil;
import com.cpms.common.util.JwtUserUtil;
import com.cpms.common.util.PageUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {

    @Value("${support.file.upload.path}")
    private String uploadPath;

    private final JwtUserUtil jwtUserUtil;

    private final EntityFinder entityFinder;

    private final SupportRequestRepository supportRequestRepository;

    private final SupportResponseRepository supportResponseRepository;

    private final SupportFileRepository supportFileRepository;

    private final CommonCodeRepository commonCodeRepository;

    private final CpmsUserRepository cpmsUserRepository;

    private final CpmsCompanyRepository cpmsCompanyRepository;

    private final CpmsProjectRepository cpmsProjectRepository;

    /**
     * 문의를 신규 등록한다.
     *
     * @param reqSupportDTO
     * @return
     */
    @Override
    @Transactional
    public void insertSupportRequest(ReqSupportDTO reqSupportDTO) {
        CpmsCompany requestCompany =
                entityFinder.findByIdOrThrow(
                        cpmsCompanyRepository, reqSupportDTO.getRequestCompanyId());

        CpmsCompany userCompany =
                entityFinder.findByIdOrThrow(cpmsCompanyRepository, jwtUserUtil.getCompanyId());

        CpmsProject requestProject =
                entityFinder.findByIdOrThrow(
                        cpmsProjectRepository, reqSupportDTO.getRequestProjectId());

        CommonCode requestCd =
                entityFinder.findByIdOrThrow(commonCodeRepository, reqSupportDTO.getRequestCd());

        CommonCode statusCd =
                entityFinder.findByIdOrThrow(commonCodeRepository, reqSupportDTO.getStatusCd());

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
                        jwtUserUtil.getUserId());

        // 유지보수 요청 저장
        supportRequestRepository.save(supportRequest);

        // 파일 업로드 처리
        if (hasFiles(reqSupportDTO.getSupportFile())) {
            String fileType = FileType.REQUEST.getCode();
            supportFileUpload(reqSupportDTO.getSupportFile(), supportRequest, fileType);
        }
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
        // 날짜형 데이터 처리
        reqSupportListDTO.setSearchStartDt(
                Optional.ofNullable(reqSupportListDTO.getSearchStartDt()).orElse(""));

        reqSupportListDTO.setSearchEndDt(
                Optional.ofNullable(reqSupportListDTO.getSearchEndDt()).orElse(""));

        if (jwtUserUtil.isUser()) {
            reqSupportListDTO.setSearchCompanyId(jwtUserUtil.getCompanyId());

        } else if (jwtUserUtil.isTemp()) {
            reqSupportListDTO.setRegId(jwtUserUtil.getUserId());
        }

        Pageable pageable =
                PageUtil.createPageable(
                        reqSupportListDTO.getPageNo(), reqSupportListDTO.getPageSize());

        Page<ResSupportListDTO.SupportList> supportListPage =
                supportRequestRepository.findSupportList(reqSupportListDTO, pageable);

        ResSupportListDTO result =
                ResSupportListDTO.builder()
                        .totalCnt((int) supportListPage.getTotalElements())
                        .supportList(supportListPage.getContent())
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

        // TEMP 권한은 자신의 글만 조회가 가능하다.
        if (jwtUserUtil.isTemp()) {
            Integer userId = jwtUserUtil.getUserId();
            Integer regId = result.getRegId();

            if (!userId.equals(regId)) {
                throw new CustomException(ErrorCode.NO_AUTHORITY);
            }
        }
        // USER 권한은 자신이 속한 업체의 요청 데이터만 조회가 가능하다.
        else if (jwtUserUtil.isUser()) {
            Integer companyId = jwtUserUtil.getCompanyId();
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
    public void insertSupportResponse(ReqSupportResponseDTO reqSupportResponseDTO) {
        SupportRequest supportRequest =
                entityFinder.findByIdOrThrow(
                        supportRequestRepository, reqSupportResponseDTO.getSupportRequestId());

        CommonCode statusCd =
                entityFinder.findByIdOrThrow(
                        commonCodeRepository, reqSupportResponseDTO.getResponseStatusCd());

        CpmsUser responseUserId =
                entityFinder.findByIdOrThrow(cpmsUserRepository, jwtUserUtil.getUserId());

        // 처리 상태 업데이트
        supportRequest.updateStatusCd(statusCd);

        // 처리 담당자 업데이트
        supportRequest.updateResponseUser(responseUserId);

        SupportResponse supportResponse =
                new SupportResponse(
                        supportRequest,
                        reqSupportResponseDTO.getResponseTitle(),
                        reqSupportResponseDTO.getResponseEditor(),
                        jwtUserUtil.getUserId());

        // 응답 저장
        supportResponseRepository.save(supportResponse);

        // 응답 첨부파일 저장
        if (hasFiles(reqSupportResponseDTO.getResponseFile())) {
            String fileType = FileType.RESPONSE.getCode();
            supportFileUpload(reqSupportResponseDTO.getResponseFile(), supportRequest, fileType);
        }
    }

    /**
     * 응답 답변을 수정한다.
     *
     * @param reqSupportResponseDTO
     * @return
     */
    @Override
    @Transactional
    public void updateSupportResponse(ReqSupportResponseDTO reqSupportResponseDTO) {
        Integer userId = jwtUserUtil.getUserId();

        // 일반 사용자는 답변을 수정 할 수 없다.
        if (jwtUserUtil.isUser()) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        CommonCode statusCd =
                entityFinder.findByIdOrThrow(
                        commonCodeRepository, reqSupportResponseDTO.getResponseStatusCd());

        SupportRequest supportRequest =
                entityFinder.findByIdOrThrow(
                        supportRequestRepository, reqSupportResponseDTO.getSupportRequestId());

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
                                String fileType = FileType.RESPONSE.getCode();

                                supportFileUpload(
                                        reqSupportResponseDTO.getResponseFile(),
                                        supportRequest,
                                        fileType);
                            }
                        },
                        () -> {
                            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
                        });
    }

    /**
     * 답변을 삭제한다.
     *
     * @param reqSupportDTO
     * @return
     */
    @Override
    @Transactional
    public void deleteSupportResponse(ReqSupportDTO reqSupportDTO) {
        Integer userId = jwtUserUtil.getUserId();
        Integer supportRequestId = reqSupportDTO.getSupportRequestId();

        // 일반 사용자는 답변의 파일을 삭제할 수 없다.
        if (jwtUserUtil.isUser()) {
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
                                            supportRequestId, FileType.RESPONSE.getCode(), YesNo.N);

                    fileList.forEach(file -> file.deleteFile(YesNo.Y, userId));
                },
                () -> {
                    throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
                });
    }

    /**
     * 'ADMIN' 권한 사용자가 문의글을 조회하면 자동으로 처리상태가 업데이트 된다.
     *
     * @param reqSupportDTO
     * @return
     */
    @Override
    @Transactional
    public void updateSupportStatus(ReqSupportDTO reqSupportDTO) {
        // ADMIN 권한일때만 상태코드를 변경한다.
        if (jwtUserUtil.isAdmin()) {
            SupportRequest supportRequest =
                    entityFinder.findByIdOrThrow(
                            supportRequestRepository, reqSupportDTO.getSupportRequestId());

            CommonCode statusCd =
                    entityFinder.findByIdOrThrow(commonCodeRepository, reqSupportDTO.getStatusCd());
            supportRequest.updateStatusCd(statusCd);

            supportRequestRepository.save(supportRequest);
        }
    }

    /**
     * 처리 담당자 업데이트
     *
     * @param reqSupportDTO
     * @return
     */
    @Override
    @Transactional
    public void updateResponseUserInfo(ReqSupportDTO reqSupportDTO) {
        SupportRequest supportRequest =
                entityFinder.findByIdOrThrow(
                        supportRequestRepository, reqSupportDTO.getSupportRequestId());

        CpmsUser responseUser =
                entityFinder.findByIdOrThrow(cpmsUserRepository, reqSupportDTO.getUserId());

        supportRequest.updateResponseUser(responseUser);

        supportRequestRepository.save(supportRequest);
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
        SupportFile file = entityFinder.findByIdOrThrow(supportFileRepository, supportFileId);

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
        if (jwtUserUtil.isUser()) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        SupportFile file = entityFinder.findByIdOrThrow(supportFileRepository, supportFileId);

        file.deleteFile(YesNo.Y, jwtUserUtil.getUserId());
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
                                jwtUserUtil.getUserId());

                supportFileRepository.save(supportFile);

            } catch (Exception e) {
                throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
            }
        }
    }
}
