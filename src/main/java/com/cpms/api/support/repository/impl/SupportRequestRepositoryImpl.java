package com.cpms.api.support.repository.impl;

import com.cpms.api.auth.model.QCpmsUser;
import com.cpms.api.code.model.QComCode;
import com.cpms.api.support.dto.req.ReqSupportListDTO;
import com.cpms.api.support.dto.res.ResSupportDetailDTO;
import com.cpms.api.support.dto.res.ResSupportListDTO;
import com.cpms.api.support.repository.CustomSupportRequestRepository;
import com.cpms.api.user.model.QCpmsCompany;
import com.cpms.api.user.model.QCpmsProject;
import com.cpms.common.helper.YesNo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SupportRequestRepositoryImpl implements CustomSupportRequestRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QComCode requestCd = new QComCode("requestCd");

    private final QComCode statusCd = new QComCode("statusCd");

    private final QCpmsUser cpmsUser = QCpmsUser.cpmsUser;

    private final QCpmsCompany requestCompany = new QCpmsCompany("requestCompany");

    private final QCpmsCompany userCompany = new QCpmsCompany("userCompany");

    private final QCpmsProject cpmsProject = QCpmsProject.cpmsProject;

    private final QSupportRequest supportRequest = QSupportRequest.supportRequest;

    private final QSupportResponse supportResponse = QSupportResponse.supportResponse;

    private final QSupportFile supportFile = QSupportFile.supportFile;

    @Override
    public Page<ResSupportListDTO.SupportList> findSupportList(
            ReqSupportListDTO reqSupportListDTO, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(supportRequest.delYn.eq(YesNo.valueOf("N")));

        // 검색 조건 설정
        if (reqSupportListDTO.getSearchCompanyId() != null && reqSupportListDTO.getSearchCompanyId() != 0) {
            builder.and(supportRequest.userCompany.companyId.eq(reqSupportListDTO.getSearchCompanyId()));
        }

        if (reqSupportListDTO.getSearchRequestCd() != null && reqSupportListDTO.getSearchRequestCd() != 0) {
            builder.and(supportRequest.requestCd.codeId.eq(reqSupportListDTO.getSearchRequestCd()));
        }

        if (reqSupportListDTO.getSearchStatusCd() != null && reqSupportListDTO.getSearchStatusCd() != 0) {
            builder.and(supportRequest.statusCd.codeId.eq(reqSupportListDTO.getSearchStatusCd()));
        }

        if (StringUtils.hasText(reqSupportListDTO.getSearchTitle())) {
            builder.and(supportRequest.supportTitle.containsIgnoreCase(reqSupportListDTO.getSearchTitle()));
        }

        if (StringUtils.hasText(reqSupportListDTO.getSearchStartDt())
                && StringUtils.hasText(reqSupportListDTO.getSearchEndDt())) {

            LocalDate startDate =
                LocalDate.parse(reqSupportListDTO.getSearchStartDt(), DateTimeFormatter.ISO_DATE);

            LocalDate endDate =
                LocalDate.parse(reqSupportListDTO.getSearchEndDt(), DateTimeFormatter.ISO_DATE);

            LocalDateTime startDt = startDate.atStartOfDay();
            LocalDateTime endDt = endDate.atTime(23, 59, 59);

            builder.and(supportRequest.regDt.between(startDt, endDt));
        }

        // 조회 쿼리
        List<ResSupportListDTO.SupportList> result =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResSupportListDTO.SupportList.class,
                                        supportRequest.suportReqId,
                                        supportRequest.userCompany.companyNm.as("userCompanyNm"),
                                        supportRequest.requestProject.projectNm.as("requestProjectNm"),
                                        supportRequest.requestCd.codeId.as("requestCd"),
                                        supportRequest.requestCd.codeNm.as("requestNm"),
                                        supportRequest.statusCd.codeId.as("statusCd"),
                                        supportRequest.statusCd.codeNm.as("statusNm"),
                                        cpmsUser.userNm.as("regUserNm"),
                                        Expressions.stringTemplate(
                                                        "DATE_FORMAT({0}, '%Y-%m-%d')",
                                                        supportRequest.requestDate)
                                                .as("requestDate"),
                                        Expressions.stringTemplate(
                                                        "DATE_FORMAT({0}, '%Y-%m-%d')",
                                                        supportRequest.regDt)
                                                .as("regDt"),
                                        supportRequest.supportTitle))
                        .from(supportRequest)
                        .leftJoin(supportRequest.userCompany, userCompany)
                        .leftJoin(supportRequest.requestProject, cpmsProject)
                        .leftJoin(supportRequest.requestCd, requestCd)
                        .leftJoin(supportRequest.statusCd, statusCd)
                        .leftJoin(supportRequest.responseUser, cpmsUser)
                        .where(builder)
                        .orderBy(supportRequest.supportRequestId.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        long total =
                jpaQueryFactory
                        .select(supportRequest.supportRequestId.count())
                        .from(supportRequest)
                        .where(builder)
                        .fetchOne();

        return new PageImpl<> (result, pageable, total);
    }

    @Override
    public ResSupportDetailDTO findSupportDetail(Integer supportRequestId) {
        ResSupportDetailDTO detail =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResSupportDetailDTO.class,
                                        supportRequest.supportRequestId,
                                        supportRequest.requestCompany.companyNm.as("requestCompanyNm"),
                                        supportRequest.userCompany.companyId.as("userCompanyId"),
                                        supportRequest.userCompany.companyNm.as("userCompanyNm"),
                                        supportRequest.requestProject.projectNm.as("requestProjectNm"),
                                        supportRequest.requestCd.codeId.as("requestCd"),
                                        supportRequest.requestCd.codeNm.as("requestNm"),
                                        supportRequest.statusCd.codeId.as("statusCd"),
                                        supportRequest.statusCd.codeNm.as("statusNm"),
                                        supportRequest.responseUser.userNm.as("responseUserNm"),
                                        Expressions.stringTemplate(
                                                        "DATE_FORMAT({0}, '%Y-%m-%d')",
                                                        supportRequest.requestDate)
                                                .as("requestDate"),
                                        supportRequest.supportTitle,
                                        supportRequest.supportEditor))
                        .from(supportRequest)
                        .leftJoin(supportRequest.requestCompany, requestCompany)
                        .leftJoin(supportRequest.userCompany, userCompany)
                        .leftJoin(supportRequest.requestProject, cpmsProject)
                        .leftJoin(supportRequest.requestCd, requestCd)
                        .leftJoin(supportRequest.statusCd, statusCd)
                        .leftJoin(supportRequest.responseUser, cpmsUser)
                        .where(supportRequest.supportRequestId.eq(supportRequestId))
                        .fetchOne();

        if (detail == null) {
            return new ResSupportDetailDTO();
        }

        ResSupportDetailDTO.SupportResponse responseDetail =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResSupportDetailDTO.SupportResponse.class,
                                        SupportResponse.supportResponseId,
                                        SupportResponse.responseTitle,
                                        SupportResponse.responseEditor))
                        .from(SupportResponse)
                        .where(
                                SupportResponse
                                        .supportRequest
                                        .supportRequestId
                                        .eq(supportRequestId)
                                        .and(supportResponse.delYn.eq(YesNo.N)))
                        .fetchOne();

        detail.setSupportResponse(responseDetail != null ? responseDetail : new ResSupportDetailDTO.SupportResponse());

        List<ResSupportDetailDTO.FileList> files =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResSupportDetailDTO.FileList.class,
                                        supportFile.supportFileId,
                                        supportFile.fileType,
                                        supportFile.filePath,
                                        supportFile.fileNm,
                                        supportFile.fileOgNm))
                        .from(supportFile)
                        .where(
                                supportFile
                                        .supportRequest
                                        .supportRequestId
                                        .eq(supportRequestId)
                                        .and(supportFile.delYn.eq(YesNo.N)))
                        .fetch();

        detail.setFileList(files != null ? files : new ArrayList<>());

        return detail;
    }
}
