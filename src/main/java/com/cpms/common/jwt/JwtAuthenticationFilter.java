package com.cpms.common.jwt;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) req;

        Optional.ofNullable(resolveToken(httpRequest))
                .filter(jwtTokenProvider::validateToken)
                .map(jwtTokenProvider::getAuthentication)
                .ifPresent(
                        authentication -> {
                            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                                SecurityContextHolder.getContext()
                                        .setAuthentication(authentication);
                            }
                        });

        chain.doFilter(req, res);
    }

    /**
     * 요청헤더에서 인증값을 가져온다.
     *
     * @param req
     * @return
     */
    private String resolveToken(HttpServletRequest req) {
        return Optional.ofNullable(req.getHeader("Authorization"))
                .filter(StringUtils::hasText)
                .filter(token -> token.toLowerCase().startsWith("bearer "))
                .map(token -> token.substring(7).trim())
                .orElse(null);
    }
}
