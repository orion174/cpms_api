package com.cpms.api.auth.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cpms.api.auth.dto.req.ReqRefreshTokenDTO;
import com.cpms.api.auth.dto.res.QResLoginDTO;
import com.cpms.api.auth.dto.res.ResLoginDTO;
import com.cpms.api.auth.model.QCpmsUser;
import com.cpms.api.auth.model.QUserLoginHistory;
import com.cpms.api.auth.repository.CustomAuthRepository;
import com.cpms.common.jwt.JwtDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomAuthRepositoryImpl implements CustomAuthRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QCpmsUser cpmsUser = QCpmsUser.cpmsUser;
    QUserLoginHistory userLoginHistory = QUserLoginHistory.userLoginHistory;

    @Override
    public Optional<ResLoginDTO> findUserByLoginId(String loginId) {
        ResLoginDTO result =
                jpaQueryFactory
                        .select(
                                new QResLoginDTO(
                                        cpmsUser.userId,
                                        cpmsUser.authType,
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
        Long loginHistoryId = reqRefreshTokenDTO.getLoginHistoryId();
        String refreshToken = reqRefreshTokenDTO.getRefreshToken();

        JwtDTO result =
                jpaQueryFactory
                        .select(
                                Projections.fields(
                                        JwtDTO.class,
                                        cpmsUser.userId,
                                        cpmsUser.companyId,
                                        cpmsUser.authType,
                                        cpmsUser.loginId))
                        .from(userLoginHistory)
                        .join(cpmsUser)
                        .on(userLoginHistory.userId.eq(cpmsUser.userId))
                        .where(
                                userLoginHistory
                                        .loginHistoryId
                                        .eq(loginHistoryId)
                                        .and(userLoginHistory.refreshToken.eq(refreshToken)))
                        .fetchOne();

        return result;
    }
}
