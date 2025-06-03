package com.cpms.common.util;

import static com.cpms.common.util.CommonUtil.parseToIntSafely;

import org.springframework.stereotype.Component;

import com.cpms.common.helper.AuthType;
import com.cpms.common.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUserUtil {

    private final JwtTokenProvider jwtTokenProvider;

    // 사용자 ID
    public Integer getUserId() {
        return parseToIntSafely(jwtTokenProvider.getClaim("userId"));
    }

    // 사용자 회사 ID
    public Integer getCompanyId() {
        return parseToIntSafely(jwtTokenProvider.getClaim("companyId"));
    }

    // 사용자 권한 타입
    public String getAuthType() {
        return jwtTokenProvider.getClaim("authType");
    }

    public boolean isUser() {
        return AuthType.USER == AuthType.fromCode(getAuthType());
    }

    public boolean isTemp() {
        return AuthType.TEMP == AuthType.fromCode(getAuthType());
    }

    public boolean isAdmin() {
        return AuthType.ADMIN == AuthType.fromCode(getAuthType());
    }

    public boolean isNotUser() {
        return AuthType.USER != AuthType.fromCode(getAuthType());
    }

    public boolean isNotTemp() {
        return AuthType.TEMP != AuthType.fromCode(getAuthType());
    }

    public boolean isNotAdmin() {
        return AuthType.ADMIN != AuthType.fromCode(getAuthType());
    }
}
