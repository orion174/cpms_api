package com.cpms.api.setting.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.cpms.api.setting.dto.request.ReqCompanyDTO;
import com.cpms.api.setting.dto.request.ReqCompanyListDTO;
import com.cpms.api.setting.dto.response.QResCompanyListDTO;
import com.cpms.api.setting.dto.response.ResCompanyListDTO;
import com.cpms.api.setting.model.QCpmsCompany;
import com.cpms.api.setting.repository.CustomCpmsCompanyRepository;
import com.cpms.cmmn.helper.YesNo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
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

    @Override
    public Page<ResCompanyListDTO> findAdminCompanylist(
            ReqCompanyListDTO reqDTO, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // 회사 명 검색
        if (StringUtils.hasText(reqDTO.getCompanyNm())) {
            builder.and(cpmsCompany.companyNm.containsIgnoreCase(reqDTO.getCompanyNm()));
        }
        // 사용 유무 검색
        if (StringUtils.hasText(reqDTO.getUseYn())) {
            builder.and(cpmsCompany.useYn.eq(YesNo.valueOf(reqDTO.getUseYn())));
        }

        builder.and(cpmsCompany.delYn.eq(YesNo.N));

        List<ResCompanyListDTO> result =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResCompanyListDTO.class,
                                        cpmsCompany.companyId,
                                        cpmsCompany.companyNm,
                                        cpmsCompany.authType,
                                        cpmsCompany.address,
                                        cpmsCompany.useYn,
                                        cpmsCompany.regDt))
                        .from(cpmsCompany)
                        .where(builder)
                        .orderBy(cpmsCompany.companyId.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        long total =
                jpaQueryFactory
                        .select(cpmsCompany.companyId.count())
                        .from(cpmsCompany)
                        .where(builder)
                        .fetchOne();

        return new PageImpl<>(result, pageable, total);
    }
}
