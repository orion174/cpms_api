package com.cpms.api.auth.repository;

import java.util.Optional;

import com.cpms.api.auth.dto.res.ResLoginDTO;

public interface CustomAuthRepository {
    Optional<ResLoginDTO> findUserByLoginId(String loginId);
}
