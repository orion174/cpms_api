package com.cpms.api.setting.repository.impl;

import java.util.List;

import org.springframework.util.StringUtils;

import com.cpms.api.setting.dto.request.ReqCompanyDTO;
import com.cpms.api.setting.dto.response.QResCompanyListDTO;
import com.cpms.api.setting.dto.response.ResCompanyListDTO;
import com.cpms.api.setting.model.QCpmsCompany;
import com.cpms.api.setting.repository.CustomCpmsCompanyRepository;
import com.cpms.common.helper.YesNo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CpmsCompanyRepositoryImpl implements CustomCpmsCompanyRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QCpmsCompany cpmsCompany = QCpmsCompany.cpmsCompany;

    @Override
    public List<ResCompanyListDTO> selectCpmsCompanyList(ReqCompanyDTO reqCompanyDTO) {
        BooleanBuilder builder = new BooleanBuilder();

        String authType = reqCompanyDTO.getAuthType();
        if (StringUtils.hasText(authType)) {
            builder.and(cpmsCompany.cpmsCompany.authType.eq(authType));
        }

        builder.and(cpmsCompany.delYn.eq(YesNo.N));

        return jpaQueryFactory
                .select(new QResCompanyListDTO(cpmsCompany.companyId, cpmsCompany.companyNm))
                .from(cpmsCompany)
                .where(builder)
                .fetch();
    }
}
