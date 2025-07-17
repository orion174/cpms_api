package com.cpms.api.user.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.cpms.api.setting.model.QCpmsCompany;
import com.cpms.api.user.dto.request.ReqUserListDTO;
import com.cpms.api.user.dto.response.ResUserListDTO;
import com.cpms.api.user.model.QCpmsUser;
import com.cpms.api.user.repository.CustomCpmsUserRepository;
import com.cpms.common.helper.YesNo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomCpmsUserRepositoryImpl implements CustomCpmsUserRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QCpmsUser cpmsUser = QCpmsUser.cpmsUser;

    private final QCpmsCompany cpmsCompany = new QCpmsCompany("cpmsCompany");

    @Override
    public Page<ResUserListDTO> findCpmsUserList(ReqUserListDTO reqDTO, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // 소속 회사
        if (reqDTO.getSearchCompanyId() != null && reqDTO.getSearchCompanyId() != 0) {
            builder.and(cpmsUser.companyId.eq(reqDTO.getSearchCompanyId()));
        }
        // 권한 등급
        if (StringUtils.hasText(reqDTO.getSearchAuthType())) {
            builder.and(cpmsUser.authType.eq(reqDTO.getSearchAuthType()));
        }
        // 계정 사용 유무
        if (StringUtils.hasText(reqDTO.getSearchUseYn())) {
            builder.and(cpmsUser.useYn.eq(YesNo.valueOf(reqDTO.getSearchUseYn())));
        }
        // 사용자 명
        if (StringUtils.hasText(reqDTO.getSearchUserNm())) {
            builder.and(cpmsUser.userNm.containsIgnoreCase(reqDTO.getSearchUserNm()));
        }

        builder.and(cpmsUser.delYn.eq(YesNo.N));

        List<ResUserListDTO> result =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        ResUserListDTO.class,
                                        cpmsUser.userId,
                                        cpmsUser.authType,
                                        cpmsUser.cpmsCompany.companyNm.as("companyNm"),
                                        cpmsUser.userNm,
                                        cpmsUser.userDept,
                                        cpmsUser.userPos,
                                        Expressions.stringTemplate(
                                                        "DATE_FORMAT({0}, '%Y-%m-%d')",
                                                        cpmsUser.regDt)
                                                .as("regDt"),
                                        cpmsUser.useYn))
                        .from(cpmsUser)
                        .leftJoin(cpmsUser.cpmsCompany, cpmsCompany)
                        .where(builder)
                        .orderBy(cpmsUser.userId.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        long total =
                jpaQueryFactory
                        .select(cpmsUser.userId.count())
                        .from(cpmsUser)
                        .where(builder)
                        .fetchOne();

        return new PageImpl<>(result, pageable, total);
    }
}
