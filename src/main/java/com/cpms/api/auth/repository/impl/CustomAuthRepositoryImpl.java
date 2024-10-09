package com.cpms.api.auth.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cpms.api.auth.dto.res.QResLoginDTO;
import com.cpms.api.auth.dto.res.ResLoginDTO;
import com.cpms.api.auth.model.QCpmsUser;
import com.cpms.api.auth.repository.CustomAuthRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomAuthRepositoryImpl implements CustomAuthRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QCpmsUser cpmsUser = QCpmsUser.cpmsUser;

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
}
