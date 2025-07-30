package com.cpms.api.auth.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cpms.api.auth.dto.request.ReqRefreshTokenDTO;
import com.cpms.api.auth.dto.response.QResLoginDTO;
import com.cpms.api.auth.dto.response.ResLoginDTO;
import com.cpms.api.auth.model.QUserLoginHistory;
import com.cpms.api.auth.repository.CustomAuthRepository;
import com.cpms.api.user.model.QCpmsUser;
import com.cpms.cmmn.helper.JwtDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomAuthRepositoryImpl implements CustomAuthRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QCpmsUser cpmsUser = QCpmsUser.cpmsUser;

    private final QUserLoginHistory userLoginHistory = QUserLoginHistory.userLoginHistory;

    @Override
    public Optional<ResLoginDTO> findUserByLoginId(String loginId) {
        ResLoginDTO result =
                jpaQueryFactory
                        .select(
                                new QResLoginDTO(
                                        cpmsUser.authType,
                                        cpmsUser.userId,
                                        cpmsUser.userNm,
                                        cpmsUser.companyId,
                                        cpmsUser.loginId,
                                        cpmsUser.loginPw,
                                        cpmsUser.useYn))
                        .from(cpmsUser)
                        .where(cpmsUser.loginId.eq(loginId))
                        .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public JwtDTO getUserInfoByLoginHistoryId(ReqRefreshTokenDTO reqRefreshTokenDTO) {
        Integer loginHistoryId = reqRefreshTokenDTO.getLoginHistoryId();

        JwtDTO result =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        JwtDTO.class,
                                        cpmsUser.userId,
                                        cpmsUser.authType,
                                        cpmsUser.companyId,
                                        cpmsUser.userNm,
                                        cpmsUser.loginId))
                        .from(userLoginHistory)
                        .join(cpmsUser)
                        .on(userLoginHistory.userId.eq(cpmsUser.userId))
                        .where(userLoginHistory.loginHistoryId.eq(loginHistoryId))
                        .fetchOne();

        return result;
    }
}
