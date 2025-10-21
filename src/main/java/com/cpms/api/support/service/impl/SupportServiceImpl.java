package com.cpms.api.support.service.impl;

import static com.cpms.cmmn.util.CommonUtil.hasFiles;

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

import com.cpms.api.code.model.CommonCode;
import com.cpms.api.code.repository.CmmnCodeRepository;
import com.cpms.api.setting.model.CpmsCompany;
import com.cpms.api.setting.model.CpmsProject;
import com.cpms.api.setting.repository.CpmsCompanyRepository;
import com.cpms.api.setting.repository.CpmsProjectRepository;
import com.cpms.api.support.dto.request.ReqInsertSupportDTO;
import com.cpms.api.support.dto.request.ReqInsertSupportResponseDTO;
import com.cpms.api.support.dto.request.ReqSupportListDTO;
import com.cpms.api.support.dto.request.ReqUpdateSupportResponseDTO;
import com.cpms.api.support.dto.response.ResSupportFileDTO;
import com.cpms.api.support.dto.response.ResSupportListDTO;
import com.cpms.api.support.dto.response.ResSupportViewDTO;
import com.cpms.api.support.model.SupportFile;
import com.cpms.api.support.model.SupportRequest;
import com.cpms.api.support.model.SupportResponse;
import com.cpms.api.support.repository.SupportFileRepository;
import com.cpms.api.support.repository.SupportRequestRepository;
import com.cpms.api.support.repository.SupportResponseRepository;
import com.cpms.api.support.service.SupportService;
import com.cpms.api.user.model.CpmsUser;
import com.cpms.api.user.repository.CpmsUserRepository;
import com.cpms.cmmn.exception.CustomException;
import com.cpms.cmmn.helper.Constants;
import com.cpms.cmmn.helper.EntityFinder;
import com.cpms.cmmn.helper.FileDTO;
import com.cpms.cmmn.helper.FileType;
import com.cpms.cmmn.helper.YesNo;
import com.cpms.cmmn.response.ErrorCode;
import com.cpms.cmmn.util.FileUtil;
import com.cpms.cmmn.util.JwtUserUtil;
import com.cpms.cmmn.util.PageUtil;

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

    private final CmmnCodeRepository cmmnCodeRepository;

    private final CpmsUserRepository cpmsUserRepository;

    private final CpmsCompanyRepository cpmsCompanyRepository;

    private final CpmsProjectRepository cpmsProjectRepository;

    /**
     * 문의 목록을 조회한다.
     *
     * @param reqSupportListDTO
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResSupportListDTO selectSupportList(ReqSupportListDTO reqDTO) {
        // 날짜형 데이터 처리
        reqDTO.setSearchStartDt(Optional.ofNullable(reqDTO.getSearchStartDt()).orElse(""));

        reqDTO.setSearchEndDt(Optional.ofNullable(reqDTO.getSearchEndDt()).orElse(""));

        if (jwtUserUtil.isUser()) {
            reqDTO.setSearchCompanyId(jwtUserUtil.getCompanyId());

        } else if (jwtUserUtil.isTemp()) {
            reqDTO.setRegId(jwtUserUtil.getUserId());
        }

        Pageable pageable = PageUtil.createPageable(reqDTO.getPageNo(), reqDTO.getPageSize());

        Page<ResSupportListDTO.SupportList> supportListPage =
                supportRequestRepository.findSupportList(reqDTO, pageable);

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
     * @param supportRequestId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResSupportViewDTO selectSupportView(Integer supportRequestId) {
        // 유효하지 않은 데이터 조회 시
        if (supportRequestId == null || supportRequestId == 0) {
            throw new CustomException(ErrorCode.INVALID_REQUEST_ID);
        }

        ResSupportViewDTO result = supportRequestRepository.findSupportView(supportRequestId);

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
     * 문의를 신규 등록한다.
     *
     * @param reqInsertSupportDTO
     * @return
     */
    @Override
    @Transactional
    public void insertSupportRequest(ReqInsertSupportDTO reqDTO) {
        CpmsCompany requestCompany =
                entityFinder.findByIdOrThrow(cpmsCompanyRepository, reqDTO.getRequestCompanyId());

        CpmsCompany userCompany =
                entityFinder.findByIdOrThrow(cpmsCompanyRepository, jwtUserUtil.getCompanyId());

        CpmsProject requestProject =
                entityFinder.findByIdOrThrow(cpmsProjectRepository, reqDTO.getRequestProjectId());

        CommonCode requestCd =
                entityFinder.findByIdOrThrow(cmmnCodeRepository, reqDTO.getRequestCd());

        CommonCode statusCd =
                entityFinder.findByIdOrThrow(cmmnCodeRepository, reqDTO.getStatusCd());

        SupportRequest supportRequest =
                new SupportRequest(
                        requestCompany,
                        userCompany,
                        requestProject,
                        null,
                        requestCd,
                        statusCd,
                        reqDTO.getRequestDate(),
                        null,
                        reqDTO.getSupportTitle(),
                        reqDTO.getSupportEditor(),
                        jwtUserUtil.getUserId());

        // 유지보수 요청 저장
        supportRequestRepository.save(supportRequest);

        // 파일 업로드 처리
        if (hasFiles(reqDTO.getSupportFile())) {
            String fileType = FileType.REQUEST.getCode();
            supportFileUpload(reqDTO.getSupportFile(), supportRequest, fileType);
        }
    }

    /**
     * 관리자 등급의 사용자가 문의글을 조회하면 처리상태가 업데이트 된다.
     *
     * @param supportRequestId
     */
    @Override
    @Transactional
    public void updateSupportStatus(Integer supportRequestId) {
        if (jwtUserUtil.isAdmin()) {
            SupportRequest supportRequest =
                    entityFinder.findByIdOrThrow(supportRequestRepository, supportRequestId);

            CommonCode statusCd =
                    entityFinder.findByIdOrThrow(cmmnCodeRepository, Constants.CHECK_STATUS_CD);

            supportRequest.updateStatusCd(statusCd);

            supportRequestRepository.save(supportRequest);
        }
    }

    /**
     * 응답을 등록 한다.
     *
     * @param reqInsertSupportResponseDTO
     * @return
     */
    @Override
    @Transactional
    public void insertSupportResponse(ReqInsertSupportResponseDTO reqDTO) {
        SupportRequest supportRequest =
                entityFinder.findByIdOrThrow(
                        supportRequestRepository, reqDTO.getSupportRequestId());

        CommonCode statusCd =
                entityFinder.findByIdOrThrow(cmmnCodeRepository, reqDTO.getResponseStatusCd());

        CpmsUser responseUserId =
                entityFinder.findByIdOrThrow(cpmsUserRepository, jwtUserUtil.getUserId());

        // 처리 상태 업데이트
        supportRequest.updateStatusCd(statusCd);

        // 처리 담당자 업데이트
        supportRequest.updateResponseUser(responseUserId);

        SupportResponse supportResponse =
                new SupportResponse(
                        supportRequest,
                        reqDTO.getResponseTitle(),
                        reqDTO.getResponseEditor(),
                        jwtUserUtil.getUserId());

        // 응답 저장
        supportResponseRepository.save(supportResponse);

        // 응답 첨부파일 저장
        if (hasFiles(reqDTO.getResponseFile())) {
            String fileType = FileType.RESPONSE.getCode();
            supportFileUpload(reqDTO.getResponseFile(), supportRequest, fileType);
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
    public void updateSupportResponse(
            Integer supportRequestId,
            Integer supportResponseId,
            ReqUpdateSupportResponseDTO reqDTO) {
        Integer userId = jwtUserUtil.getUserId();

        // 일반 사용자는 답변을 수정 할 수 없다.
        if (jwtUserUtil.isUser()) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        CommonCode statusCd =
                entityFinder.findByIdOrThrow(cmmnCodeRepository, reqDTO.getResponseStatusCd());

        SupportRequest supportRequest =
                entityFinder.findByIdOrThrow(supportRequestRepository, supportRequestId);

        // 답변 문의 본문 처리상태 업데이트
        supportRequest.updateStatusCd(statusCd);

        // 답변 수정
        supportResponseRepository
                .findById(supportResponseId)
                .ifPresentOrElse(
                        supportResponse -> {
                            supportResponse.updateResponse(
                                    reqDTO.getResponseTitle(), reqDTO.getResponseEditor(), userId);

                            // 파일이 있는 경우 업로드 처리
                            if (hasFiles(reqDTO.getResponseFile())) {
                                String fileType = FileType.RESPONSE.getCode();

                                supportFileUpload(
                                        reqDTO.getResponseFile(), supportRequest, fileType);
                            }
                        },
                        () -> {
                            throw new CustomException(ErrorCode.ENTITY_NOT_FOUND);
                        });
    }

    /**
     * 답변을 삭제한다.
     *
     * @param supportRequestId
     */
    @Override
    @Transactional
    public void deleteSupportResponse(Integer supportRequestId) {
        Integer userId = jwtUserUtil.getUserId();

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
