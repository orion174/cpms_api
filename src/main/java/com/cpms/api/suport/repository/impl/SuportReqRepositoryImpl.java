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
import com.cpms.common.helper.YesNo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
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

        builder.and(suportReq.delYn.eq(YesNo.valueOf("N")));

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
                                        suportReq.suportReqId.as("suportReqId"),
                                        Expressions.as(
                                                JPAExpressions.select(cpmsCompany.companyNm)
                                                        .from(cpmsCompany)
                                                        .where(
                                                                cpmsCompany.companyId.eq(
                                                                        suportReq
                                                                                .userCompany
                                                                                .companyId)),
                                                "userCompanyNm"),
                                        Expressions.as(
                                                JPAExpressions.select(cpmsProject.projectNm)
                                                        .from(cpmsProject)
                                                        .where(
                                                                cpmsProject.projectId.eq(
                                                                        suportReq
                                                                                .reqProject
                                                                                .projectId)),
                                                "reqProjectNm"),
                                        Expressions.as(
                                                JPAExpressions.select(requestCdDetail.codeNm)
                                                        .from(requestCdDetail)
                                                        .where(
                                                                requestCdDetail
                                                                        .masterCodeId
                                                                        .eq("10")
                                                                        .and(
                                                                                requestCdDetail
                                                                                        .codeId.eq(
                                                                                        suportReq
                                                                                                .requestCdDetail
                                                                                                .codeId))),
                                                "requestCdNm"),
                                        Expressions.as(
                                                JPAExpressions.select(statusCdDetail.codeNm)
                                                        .from(statusCdDetail)
                                                        .where(
                                                                statusCdDetail
                                                                        .masterCodeId
                                                                        .eq("20")
                                                                        .and(
                                                                                statusCdDetail
                                                                                        .codeId.eq(
                                                                                        suportReq
                                                                                                .statusCdDetail
                                                                                                .codeId))),
                                                "statusCdNm"),
                                        Expressions.as(
                                                JPAExpressions.select(cpmsUser.userNm)
                                                        .from(cpmsUser)
                                                        .where(
                                                                cpmsUser.userId.eq(
                                                                        suportReq.regUser.userId)),
                                                "regUserNm"),
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
                                                .as("resDate"),
                                        suportReq.suportTitle))
                        .from(suportReq)
                        .where(builder)
                        .orderBy(suportReq.suportReqId.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        // 전체 데이터 개수 조회
        long total =
                jpaQueryFactory
                        .select(suportReq.suportReqId.count())
                        .from(suportReq)
                        .where(builder)
                        .fetchOne();

        return new PageImpl<>(result, pageable, total);
    }
}
