package com.cpms.cmmn.jwt;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {
        // Api Url 접근시, 인증정보가 없으면 401 에러 반환
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("TOKEN_ERROR");
    }
}
