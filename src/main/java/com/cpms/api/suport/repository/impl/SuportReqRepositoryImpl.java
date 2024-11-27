package com.cpms.api.suport.repository.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.cpms.api.auth.model.QCpmsUser;
import com.cpms.api.code.model.QComCode;
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
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SuportReqRepositoryImpl implements CustomSuportReqRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QComCode requestCd = new QComCode("requestCd");

    private final QComCode statusCd = new QComCode("statusCd");

    private final QCpmsUser cpmsUser = QCpmsUser.cpmsUser;

    private final QCpmsCompany reqCompany = new QCpmsCompany("reqCompany");

    private final QCpmsCompany userCompany = new QCpmsCompany("userCompany");

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
        if (reqSuportListDTO.getSchRequestCd() != null && reqSuportListDTO.getSchRequestCd() != 0) {
            builder.and(suportReq.requestCd.codeId.eq(reqSuportListDTO.getSchRequestCd()));
        }
        if (reqSuportListDTO.getSchStatusCd() != null && reqSuportListDTO.getSchStatusCd() != 0) {
            builder.and(suportReq.statusCd.codeId.eq(reqSuportListDTO.getSchStatusCd()));
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
                                        suportReq.suportReqId,
                                        suportReq.userCompany.companyNm.as("userCompanyNm"),
                                        suportReq.reqProject.projectNm.as("reqProjectNm"),
                                        suportReq.requestCd.codeId.as("requestCd"),
                                        suportReq.requestCd.codeNm.as("requestCdNm"),
                                        suportReq.statusCd.codeId.as("statusCd"),
                                        suportReq.statusCd.codeNm.as("statusCdNm"),
                                        cpmsUser.userNm.as("regUserNm"),
                                        Expressions.stringTemplate(
                                                        "DATE_FORMAT({0}, '%Y-%m-%d')",
                                                        suportReq.reqDate)
                                                .as("reqDate"),
                                        Expressions.stringTemplate(
                                                        "DATE_FORMAT({0}, '%Y-%m-%d')",
                                                        suportReq.regDt)
                                                .as("regDt"),
                                        suportReq.suportTitle))
                        .from(suportReq)
                        .leftJoin(suportReq.userCompany, userCompany)
                        .leftJoin(suportReq.reqProject, cpmsProject)
                        .leftJoin(suportReq.requestCd, requestCd)
                        .leftJoin(suportReq.statusCd, statusCd)
                        .leftJoin(suportReq.resUser, cpmsUser)
                        .where(builder)
                        .orderBy(suportReq.suportReqId.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

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
        ResSuportDetailDTO detail =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResSuportDetailDTO.class,
                                        suportReq.suportReqId,
                                        suportReq.reqCompany.companyNm.as("reqCompanyNm"),
                                        suportReq.userCompany.companyNm.as("userCompanyNm"),
                                        suportReq.reqProject.projectNm.as("reqProjectNm"),
                                        suportReq.requestCd.codeId.as("requestCd"),
                                        suportReq.requestCd.codeNm.as("requestCdNm"),
                                        suportReq.statusCd.codeId.as("statusCd"),
                                        suportReq.statusCd.codeNm.as("statusCdNm"),
                                        suportReq.resUser.userNm.as("resUserNm"),
                                        Expressions.stringTemplate(
                                                        "DATE_FORMAT({0}, '%Y-%m-%d')",
                                                        suportReq.reqDate)
                                                .as("reqDate"),
                                        suportReq.suportTitle,
                                        suportReq.suportEditor))
                        .from(suportReq)
                        .leftJoin(suportReq.reqCompany, reqCompany)
                        .leftJoin(suportReq.userCompany, userCompany)
                        .leftJoin(suportReq.reqProject, cpmsProject)
                        .leftJoin(suportReq.requestCd, requestCd)
                        .leftJoin(suportReq.statusCd, statusCd)
                        .leftJoin(suportReq.resUser, cpmsUser)
                        .where(suportReq.suportReqId.eq(suportReqId))
                        .fetchOne();

        if (detail == null) {
            return new ResSuportDetailDTO();
        }

        ResSuportDetailDTO.SuportRes resDetail =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResSuportDetailDTO.SuportRes.class,
                                        suportRes.suportResId,
                                        suportRes.resTitle,
                                        suportRes.resEditor))
                        .from(suportRes)
                        .where(
                                suportRes
                                        .suportReq
                                        .suportReqId
                                        .eq(suportReqId)
                                        .and(suportRes.delYn.eq(YesNo.N)))
                        .fetchOne();

        detail.setSuportRes(resDetail != null ? resDetail : new ResSuportDetailDTO.SuportRes());

        List<ResSuportDetailDTO.FileList> files =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResSuportDetailDTO.FileList.class,
                                        suportFile.suportFileId,
                                        suportFile.fileType,
                                        suportFile.filePath,
                                        suportFile.fileNm,
                                        suportFile.fileOgNm))
                        .from(suportFile)
                        .where(
                                suportFile
                                        .suportReq
                                        .suportReqId
                                        .eq(suportReqId)
                                        .and(suportFile.delYn.eq(YesNo.N)))
                        .fetch();

        detail.setFileList(files != null ? files : new ArrayList<>());

        return detail;
    }
}
