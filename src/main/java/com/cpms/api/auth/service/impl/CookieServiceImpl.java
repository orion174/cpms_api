package com.cpms.api.auth.service.impl;

import java.util.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.cpms.api.auth.dto.response.ResCookieDTO;
import com.cpms.api.auth.service.CookieService;
import com.cpms.common.config.CorsProperties;
import com.cpms.common.util.CookieUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {

    private final CorsProperties corsProperties;

    /**
     * 쿠키를 조회한다.
     *
     * @param request
     * @return
     */
    public ResCookieDTO getCookies(HttpServletRequest request) {
        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[] {});

        Map<String, String> result = new HashMap<>();

        for (Cookie cookie : cookies) {
            result.put(cookie.getName(), cookie.getValue());
        }

        return new ResCookieDTO(result);
    }

    /**
     * 쿠키를 삭제한다.
     *
     * @param request
     * @param response
     */
    public void deleteCookies(HttpServletRequest request, HttpServletResponse response) {
        String domain = CorsProperties.extractDomain(corsProperties.getAllowedOrigins().get(0));

        Arrays.stream(Optional.ofNullable(request.getCookies()).orElse(new Cookie[] {}))
                .filter(cookie -> !cookie.getName().contains("adm"))
                .forEach(cookie -> CookieUtil.deleteCookie(response, cookie.getName(), domain));
    }
}
