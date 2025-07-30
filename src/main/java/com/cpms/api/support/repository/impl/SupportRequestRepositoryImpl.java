package com.cpms.api.support.repository.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.cpms.api.code.model.QCommonCode;
import com.cpms.api.setting.model.QCpmsCompany;
import com.cpms.api.setting.model.QCpmsProject;
import com.cpms.api.support.dto.request.ReqSupportListDTO;
import com.cpms.api.support.dto.response.ResSupportListDTO;
import com.cpms.api.support.dto.response.ResSupportViewDTO;
import com.cpms.api.support.model.QSupportFile;
import com.cpms.api.support.model.QSupportRequest;
import com.cpms.api.support.model.QSupportResponse;
import com.cpms.api.support.repository.CustomSupportRequestRepository;
import com.cpms.api.user.model.QCpmsUser;
import com.cpms.cmmn.helper.YesNo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SupportRequestRepositoryImpl implements CustomSupportRequestRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QCommonCode requestCd = new QCommonCode("requestCd");

    private final QCommonCode statusCd = new QCommonCode("statusCd");

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

        if (reqSupportListDTO.getRegId() != null && reqSupportListDTO.getRegId() != 0) {
            builder.and(supportRequest.regId.eq(reqSupportListDTO.getRegId()));
        }

        if (reqSupportListDTO.getSearchCompanyId() != null
                && reqSupportListDTO.getSearchCompanyId() != 0) {
            builder.and(
                    supportRequest.userCompany.companyId.eq(
                            reqSupportListDTO.getSearchCompanyId()));
        }

        if (reqSupportListDTO.getSearchRequestCd() != null
                && reqSupportListDTO.getSearchRequestCd() != 0) {
            builder.and(supportRequest.requestCd.codeId.eq(reqSupportListDTO.getSearchRequestCd()));
        }

        if (reqSupportListDTO.getSearchStatusCd() != null
                && reqSupportListDTO.getSearchStatusCd() != 0) {
            builder.and(supportRequest.statusCd.codeId.eq(reqSupportListDTO.getSearchStatusCd()));
        }

        if (StringUtils.hasText(reqSupportListDTO.getSearchStartDt())
                && StringUtils.hasText(reqSupportListDTO.getSearchEndDt())) {

            LocalDate startDate =
                    LocalDate.parse(
                            reqSupportListDTO.getSearchStartDt(), DateTimeFormatter.ISO_DATE);

            LocalDate endDate =
                    LocalDate.parse(reqSupportListDTO.getSearchEndDt(), DateTimeFormatter.ISO_DATE);

            LocalDateTime startDt = startDate.atStartOfDay();
            LocalDateTime endDt = endDate.atTime(23, 59, 59);

            builder.and(supportRequest.regDt.between(startDt, endDt));
        }

        if (StringUtils.hasText(reqSupportListDTO.getSearchTitle())) {
            builder.and(
                    supportRequest.supportTitle.containsIgnoreCase(
                            reqSupportListDTO.getSearchTitle()));
        }

        // 조회 쿼리
        List<ResSupportListDTO.SupportList> result =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResSupportListDTO.SupportList.class,
                                        supportRequest.supportRequestId,
                                        supportRequest.userCompany.companyNm.as("userCompanyNm"),
                                        supportRequest.requestProject.projectNm.as(
                                                "requestProjectNm"),
                                        supportRequest.responseUser.userNm.as("responseUserNm"),
                                        supportRequest.requestCd.codeId.as("requestCd"),
                                        supportRequest.requestCd.codeNm.as("requestNm"),
                                        supportRequest.statusCd.codeId.as("statusCd"),
                                        supportRequest.statusCd.codeNm.as("statusNm"),
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

        return new PageImpl<>(result, pageable, total);
    }

    @Override
    public ResSupportViewDTO findSupportView(Integer supportRequestId) {
        ResSupportViewDTO requestView =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResSupportViewDTO.class,
                                        supportRequest.supportRequestId,
                                        supportRequest.requestCompany.companyNm.as(
                                                "requestCompanyNm"),
                                        supportRequest.userCompany.companyId.as("userCompanyId"),
                                        supportRequest.userCompany.companyNm.as("userCompanyNm"),
                                        supportRequest.requestProject.projectNm.as(
                                                "requestProjectNm"),
                                        supportRequest.responseUser.userNm.as("responseUserNm"),
                                        supportRequest.requestCd.codeId.as("requestCd"),
                                        supportRequest.requestCd.codeNm.as("requestNm"),
                                        supportRequest.statusCd.codeId.as("statusCd"),
                                        supportRequest.statusCd.codeNm.as("statusNm"),
                                        Expressions.stringTemplate(
                                                        "DATE_FORMAT({0}, '%Y-%m-%d')",
                                                        supportRequest.requestDate)
                                                .as("requestDate"),
                                        Expressions.stringTemplate(
                                                        "DATE_FORMAT({0}, '%Y-%m-%d')",
                                                        supportRequest.responseDate)
                                                .as("responseDate"),
                                        supportRequest.supportTitle,
                                        supportRequest.supportEditor,
                                        supportRequest.regId))
                        .from(supportRequest)
                        .leftJoin(supportRequest.requestCompany, requestCompany)
                        .leftJoin(supportRequest.userCompany, userCompany)
                        .leftJoin(supportRequest.requestProject, cpmsProject)
                        .leftJoin(supportRequest.requestCd, requestCd)
                        .leftJoin(supportRequest.statusCd, statusCd)
                        .leftJoin(supportRequest.responseUser, cpmsUser)
                        .where(supportRequest.supportRequestId.eq(supportRequestId))
                        .fetchOne();

        if (requestView == null) {
            return new ResSupportViewDTO();
        }

        ResSupportViewDTO.SupportResponse responseView =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResSupportViewDTO.SupportResponse.class,
                                        supportResponse.supportResponseId,
                                        supportResponse.responseTitle,
                                        supportResponse.responseEditor))
                        .from(supportResponse)
                        .where(
                                supportResponse
                                        .supportRequest
                                        .supportRequestId
                                        .eq(supportRequestId)
                                        .and(supportResponse.delYn.eq(YesNo.N)))
                        .fetchOne();

        requestView.setSupportResponse(
                responseView != null ? responseView : new ResSupportViewDTO.SupportResponse());

        List<ResSupportViewDTO.FileList> files =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResSupportViewDTO.FileList.class,
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

        requestView.setFileList(files != null ? files : new ArrayList<>());

        return requestView;
    }
}
