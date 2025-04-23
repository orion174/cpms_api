package com.cpms.api.setting.repository.impl;

import java.util.List;

import com.cpms.api.setting.dto.request.ReqProjectDTO;
import com.cpms.api.setting.dto.response.QResProjectListDTO;
import com.cpms.api.setting.dto.response.ResProjectListDTO;
import com.cpms.api.setting.model.QCpmsProject;
import com.cpms.api.setting.repository.CustomCpmsProjectRepository;
import com.cpms.common.helper.YesNo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CpmsProjectRepositoryImpl implements CustomCpmsProjectRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QCpmsProject cpmsProject = QCpmsProject.cpmsProject;

    @Override
    public List<ResProjectListDTO> selectCpmsProjectList(ReqProjectDTO reqProjectDTO) {

        BooleanBuilder builder = new BooleanBuilder();

        Integer companyId = reqProjectDTO.getCompanyId();

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
}
