package com.cpms.api.setting.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.cpms.api.setting.dto.request.ReqProjectDTO;
import com.cpms.api.setting.dto.request.ReqProjectListDTO;
import com.cpms.api.setting.dto.response.QResProjectListDTO;
import com.cpms.api.setting.dto.response.ResProjectListDTO;
import com.cpms.api.setting.model.QCpmsCompany;
import com.cpms.api.setting.model.QCpmsProject;
import com.cpms.api.setting.repository.CustomCpmsProjectRepository;
import com.cpms.cmmn.helper.YesNo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CpmsProjectRepositoryImpl implements CustomCpmsProjectRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QCpmsProject cpmsProject = QCpmsProject.cpmsProject;

    private final QCpmsCompany cpmsCompany = QCpmsCompany.cpmsCompany;

    @Override
    public List<ResProjectListDTO> selectCpmsProjectList(ReqProjectDTO reqDTO) {

        BooleanBuilder builder = new BooleanBuilder();

        Integer companyId = reqDTO.getCompanyId();

        if (companyId != null && companyId != 0) {
            builder.and(cpmsProject.cpmsCompany.companyId.eq(companyId));
        }

        builder.and(cpmsProject.delYn.eq(YesNo.N));

        return jpaQueryFactory
                .select(new QResProjectListDTO(cpmsProject.projectId, cpmsProject.projectNm))
                .from(cpmsProject)
                .where(builder)
                .fetch();
    }

    @Override
    public Page<ResProjectListDTO> findAdminProjectlist(
            ReqProjectListDTO reqDTO, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();

        Integer companyId = reqDTO.getCompanyId();

        // 업체 별 프로젝트 검색
        if (companyId != null && companyId != 0) {
            builder.and(cpmsProject.cpmsCompany.companyId.eq(companyId));
        }
        // 진행중인 프로젝트 검색
        if (StringUtils.hasText(reqDTO.getProgressYn())) {
            builder.and(cpmsProject.progressYn.eq(YesNo.valueOf(reqDTO.getProgressYn())));
        }
        // 프로젝트 명 검색
        if (StringUtils.hasText(reqDTO.getProjectNm())) {
            builder.and(cpmsProject.projectNm.containsIgnoreCase(reqDTO.getProjectNm()));
        }

        builder.and(cpmsProject.delYn.eq(YesNo.N));

        List<ResProjectListDTO> result =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResProjectListDTO.class,
                                        cpmsProject.projectId,
                                        cpmsProject.cpmsCompany.companyNm.as("companyNm"),
                                        cpmsProject.projectNm,
                                        cpmsProject.projectInfo,
                                        cpmsProject.progressYn,
                                        cpmsProject.regDt))
                        .from(cpmsProject)
                        .leftJoin(cpmsProject.cpmsCompany, cpmsCompany)
                        .where(builder)
                        .orderBy(cpmsProject.projectId.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        long total =
                jpaQueryFactory
                        .select(cpmsProject.projectId.count())
                        .from(cpmsProject)
                        .where(builder)
                        .fetchOne();

        return new PageImpl<>(result, pageable, total);
    }
}
