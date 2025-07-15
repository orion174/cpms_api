package com.cpms.common.util;

import static com.cpms.common.helper.Constants.*;
import static com.cpms.common.util.CommonUtil.parseToIntSafely;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.cpms.common.helper.AuthType;
import com.cpms.common.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUserUtil {

    private final JwtTokenProvider jwtTokenProvider;

    private String getCurrentToken() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(auth -> auth.getCredentials().toString()) // token은 credentials에 있음
                .orElse(null);
    }

    private String getClaim(String key) {
        String token = getCurrentToken();
        return jwtTokenProvider.getClaim(token, key);
    }

    public Integer getUserId() {
        return parseToIntSafely(getClaim(CLAIM_USER_ID));
    }

    public Integer getCompanyId() {
        return parseToIntSafely(getClaim(CLAIM_COMPANY_ID));
    }

    public String getAuthType() {
        return getClaim(CLAIM_AUTH_TYPE);
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
        return !isUser();
    }

    public boolean isNotTemp() {
        return !isTemp();
    }

    public boolean isNotAdmin() {
        return !isAdmin();
    }
}
