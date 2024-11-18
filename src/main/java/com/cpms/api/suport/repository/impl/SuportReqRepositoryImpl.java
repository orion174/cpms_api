package com.cpms.api.suport.repository.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.cpms.api.suport.model.SuportReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.cpms.api.auth.model.QCpmsUser;
import com.cpms.api.code.model.QComCodeDetail;
import com.cpms.api.suport.dto.req.ReqSuportListDTO;
import com.cpms.api.suport.dto.res.ResSuportDetailDTO;
import com.cpms.api.suport.dto.res.ResSuportListDTO;
import com.cpms.api.suport.model.QSuportFile;
import com.cpms.api.suport.model.QSuportReq;
import com.cpms.api.suport.model.QSuportRes;
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
    private final QSuportRes suportRes = QSuportRes.suportRes;
    private final QSuportFile suportFile = QSuportFile.suportFile;

    /**
     * 유지보수 요청 문의 리스트
     *
     * @param reqSuportListDTO
     * @param pageable
     * @return
     */
    @Override
    public Page<ResSuportListDTO.SuportList> findSuportList(
            ReqSuportListDTO reqSuportListDTO, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(suportReq.delYn.eq(YesNo.valueOf("N")));

        // 검색 조건 설정
        if (reqSuportListDTO.getSchCompanyId() != null && reqSuportListDTO.getSchCompanyId() != 0) {
            builder.and(suportReq.userCompany.companyId.eq(reqSuportListDTO.getSchCompanyId()));
        }
        if (StringUtils.hasText(reqSuportListDTO.getSchRequestCd())) {
            builder.and(suportReq.requestCdDetail.codeId.eq(reqSuportListDTO.getSchRequestCd()));
        }
        if (StringUtils.hasText(reqSuportListDTO.getSchStatusCd())) {
            builder.and(suportReq.statusCdDetail.codeId.eq(reqSuportListDTO.getSchStatusCd()));
        }
        if (StringUtils.hasText(reqSuportListDTO.getSchTitle())) {
            builder.and(suportReq.suportTitle.containsIgnoreCase(reqSuportListDTO.getSchTitle()));
        }
        if (StringUtils.hasText(reqSuportListDTO.getSchStartDt())
                && StringUtils.hasText(reqSuportListDTO.getSchEndDt())) {

            LocalDate startDate =
                    LocalDate.parse(reqSuportListDTO.getSchStartDt(), DateTimeFormatter.ISO_DATE);
            LocalDate endDate =
                    LocalDate.parse(reqSuportListDTO.getSchEndDt(), DateTimeFormatter.ISO_DATE);

            LocalDateTime startDt = startDate.atStartOfDay();
            LocalDateTime endDt = endDate.atTime(23, 59, 59);

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
                                                JPAExpressions.select(requestCdDetail.codeId)
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
                                                "requestCd"),
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
                                                JPAExpressions.select(statusCdDetail.codeId)
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
                                                "statusCd"),
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
                                                                        suportReq.resUser.userId)),
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

    @Override
    public ResSuportDetailDTO findSuportDetail(Integer suportReqId) {
        ResSuportDetailDTO resSuportDetailDTO =
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        ResSuportDetailDTO.class,
                                        suportReq.suportReqId,
                                        suportReq.reqCompany.companyId,
                                        suportReq.userCompany.companyId,
                                        suportReq.reqProject.projectId,
                                        suportReq.resUser.userId,
                                        suportReq.requestCdDetail.codeId,
                                        suportReq.statusCdDetail.codeId,
                                        suportReq.reqDate.stringValue(),
                                        suportReq.resDate.stringValue(),
                                        suportReq.suportTitle,
                                        suportReq.suportEditor,
                                        suportRes.resEditor))
                        .from(suportReq)
                        .leftJoin(suportReq.suportRes, suportRes)
                        .where(suportReq.suportReqId.eq(suportReqId))
                        .fetchOne();

        // 첨부파일 쿼리
        List<ResSuportDetailDTO.FileList> files =
                jpaQueryFactory
                        .select(Projections.constructor(
                                ResSuportDetailDTO.FileList.class,
                                suportFile.suportFileId,
                                suportFile.suportReq.suportReqId,
                                suportFile.fileType,
                                suportFile.filePath,
                                suportFile.fileNm,
                                suportFile.fileOgNm,
                                suportFile.fileExt,
                                suportFile.fileSize.intValue()
                        ))
                        .from(suportFile)
                        .where(suportFile.suportReq.suportReqId.eq(suportReqId))
                        .fetch();

        // 파일 리스트 추가
        resSuportDetailDTO.setFileList(files);

        return resSuportDetailDTO;
    }
}
