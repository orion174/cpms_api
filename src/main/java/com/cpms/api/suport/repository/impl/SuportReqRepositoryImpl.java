package com.cpms.api.suport.repository.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.cpms.api.auth.model.QCpmsUser;
import com.cpms.api.code.model.QComCodeDetail;
import com.cpms.api.suport.dto.req.ReqSuportListDTO;
import com.cpms.api.suport.dto.res.ResSuportListDTO;
import com.cpms.api.suport.model.QSuportReq;
import com.cpms.api.suport.repository.CustomSuportReqRepository;
import com.cpms.api.user.model.QCpmsCompany;
import com.cpms.api.user.model.QCpmsProject;
import com.cpms.common.util.PagingUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SuportReqRepositoryImpl implements CustomSuportReqRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QComCodeDetail requestCdDetail = new QComCodeDetail("requestCdNm");
    private final QComCodeDetail statusCdDetail = new QComCodeDetail("statusCdNm");
    private final QCpmsUser cpmsUser = QCpmsUser.cpmsUser;
    private final QCpmsCompany cpmsCompany = QCpmsCompany.cpmsCompany;
    private final QCpmsProject cpmsProject = QCpmsProject.cpmsProject;
    private final QSuportReq suportReq = QSuportReq.suportReq;

    @Override
    public Page<ResSuportListDTO.SuportList> findSuportList(
            ReqSuportListDTO reqSuportListDTO, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // 검색 조건 설정
        if (reqSuportListDTO.getSchCompanyId() != null) {
            builder.and(
                    suportReq.userCompany.companyId.eq(reqSuportListDTO.getSchCompanyId())); // 수정
        }
        if (reqSuportListDTO.getSchRequestCd() != null) {
            builder.and(
                    suportReq.requestCdDetail.codeId.eq(reqSuportListDTO.getSchRequestCd())); // 수정
        }
        if (reqSuportListDTO.getSchStatusCd() != null) {
            builder.and(
                    suportReq.statusCdDetail.codeId.eq(reqSuportListDTO.getSchStatusCd())); // 수정
        }
        if (reqSuportListDTO.getSchTitle() != null) {
            builder.and(suportReq.suportTitle.containsIgnoreCase(reqSuportListDTO.getSchTitle()));
        }

        // 날짜 조건 설정
        if (reqSuportListDTO.getSchStartDt() != null && reqSuportListDTO.getSchEndDt() != null) {
            LocalDateTime startDt =
                    LocalDateTime.parse(
                            reqSuportListDTO.getSchStartDt(), DateTimeFormatter.ISO_DATE_TIME);
            LocalDateTime endDt =
                    LocalDateTime.parse(
                            reqSuportListDTO.getSchEndDt(), DateTimeFormatter.ISO_DATE_TIME);
            builder.and(suportReq.regDt.between(startDt, endDt));
        }

        // 조회 쿼리
        List<ResSuportListDTO.SuportList> result =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResSuportListDTO.SuportList.class,
                                        suportReq.suportReqId,
                                        suportReq.userCompany.companyNm.as("userCompanyNm"),
                                        suportReq.reqProject.projectNm.as("reqProjectNm"),
                                        suportReq.requestCdDetail.codeNm.as("requestCdNm"),
                                        suportReq.statusCdDetail.codeNm.as("statusCdNm"),
                                        suportReq.regUser.userNm.as("regUserNm"),
                                        Expressions.stringTemplate(
                                                        "DATE_FORMAT({0}, '%Y-%m-%d')",
                                                        suportReq.regDt)
                                                .as("regDt"),
                                        Expressions.stringTemplate(
                                                        "DATE_FORMAT({0}, '%Y-%m-%d')",
                                                        suportReq.reqDate)
                                                .as("reqDate"),
                                        Expressions.stringTemplate(
                                                        "DATE_FORMAT({0}, '%Y-%m-%d')",
                                                        suportReq.resDate)
                                                .as("resDate")))
                        .from(suportReq)
                        .leftJoin(suportReq.userCompany, cpmsCompany)
                        .on(cpmsCompany.isNotNull())
                        .leftJoin(suportReq.reqProject, cpmsProject)
                        .on(cpmsProject.isNotNull())
                        .leftJoin(suportReq.requestCdDetail, requestCdDetail)
                        .on(requestCdDetail.masterCodeId.eq("10"))
                        .leftJoin(suportReq.statusCdDetail, statusCdDetail)
                        .on(statusCdDetail.masterCodeId.eq("20"))
                        .leftJoin(suportReq.regUser, cpmsUser)
                        .on(cpmsUser.isNotNull())
                        .where(builder)
                        .orderBy(
                                PagingUtil.getOrderBy(
                                        pageable.getSort(), QSuportReq.class, "suportReq"))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        // 전체 데이터 개수 조회
        long total =
                jpaQueryFactory
                        .select(suportReq.count())
                        .from(suportReq)
                        .leftJoin(suportReq.userCompany, cpmsCompany)
                        .on(cpmsCompany.isNotNull())
                        .leftJoin(suportReq.reqProject, cpmsProject)
                        .on(cpmsProject.isNotNull())
                        .leftJoin(suportReq.requestCdDetail, requestCdDetail)
                        .on(requestCdDetail.masterCodeId.eq("10"))
                        .leftJoin(suportReq.statusCdDetail, statusCdDetail)
                        .on(statusCdDetail.masterCodeId.eq("20"))
                        .leftJoin(suportReq.regUser, cpmsUser)
                        .on(cpmsUser.isNotNull())
                        .where(builder)
                        .fetchOne();

        return new PageImpl<>(result, pageable, total);
    }
}
