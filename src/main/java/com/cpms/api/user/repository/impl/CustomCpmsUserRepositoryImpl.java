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
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomCpmsUserRepositoryImpl implements CustomCpmsUserRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QCpmsUser cpmsUser = QCpmsUser.cpmsUser;

    private final QCpmsCompany cpmsCompany = new QCpmsCompany("cpmsCompany");

    @Override
    public Page<ResUserListDTO> findCpmsUserList(ReqUserListDTO reqDto, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(cpmsUser.delYn.eq(YesNo.valueOf("N")));

        // 소속 회사
        if (reqDto.getSeachCompanyId() != null && reqDto.getSeachCompanyId() != 0) {
            builder.and(cpmsUser.companyId.eq(reqDto.getSeachCompanyId()));
        }
        // 권한 등급
        if (StringUtils.hasText(reqDto.getSearchAuthType())) {
            builder.and(cpmsUser.authType.eq(reqDto.getSearchAuthType()));
        }
        // 계정 사용 유무
        if (StringUtils.hasText(reqDto.getSearchUseYn())) {
            builder.and(cpmsUser.useYn.eq(YesNo.valueOf(reqDto.getSearchUseYn())));
        }
        // 사용자 명
        if (StringUtils.hasText(reqDto.getSearchUserNm())) {
            builder.and(cpmsUser.userNm.containsIgnoreCase(reqDto.getSearchUserNm()));
        }

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
