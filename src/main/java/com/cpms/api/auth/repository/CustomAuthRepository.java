package com.cpms.api.auth.repository;

import java.util.Optional;

import com.cpms.api.auth.dto.request.ReqRefreshTokenDTO;
import com.cpms.api.auth.dto.response.ResLoginDTO;
import com.cpms.cmmn.helper.JwtDTO;

public interface CustomAuthRepository {

    Optional<ResLoginDTO> findUserByLoginId(String loginId);

    JwtDTO getUserInfoByLoginHistoryId(ReqRefreshTokenDTO reqRefreshTokenDTO);
}
