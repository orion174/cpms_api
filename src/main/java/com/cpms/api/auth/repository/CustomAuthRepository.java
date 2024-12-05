package com.cpms.api.auth.repository;

import java.util.Optional;

import com.cpms.api.auth.dto.req.ReqRefreshTokenDTO;
import com.cpms.api.auth.dto.res.ResLoginDTO;
import com.cpms.common.helper.JwtDTO;

public interface CustomAuthRepository {

    Optional<ResLoginDTO> findUserByLoginId(String loginId);

    JwtDTO getUserInfoByLoginHistoryId(ReqRefreshTokenDTO reqRefreshTokenDTO);
}
