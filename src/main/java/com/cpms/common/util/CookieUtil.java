package com.cpms.common.util;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CookieUtil {

    /**
     * 리프레쉬 토큰을 쿠키에 저장한다.
     *
     * @param res
     * @param refreshToken
     * @param maxAge
     * @param secure
     */
    public static void saveRefreshCookie(
            HttpServletResponse res, String refreshToken, int maxAge, boolean secure) {
        ResponseCookie cookie =
                ResponseCookie.from("refreshToken", refreshToken)
                        .httpOnly(true)
                        .secure(secure)
                        .sameSite("Lax")
                        .path("/")
                        .maxAge(maxAge)
                        .build();

        res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    /**
     * 전체 쿠키를 삭제한다.
     *
     * @param response
     * @param name
     * @param domain
     */
    public static void deleteCookie(HttpServletResponse response, String name, String domain) {
        ResponseCookie cookie =
                ResponseCookie.from(name, "")
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .maxAge(0)
                        .sameSite("None")
                        .domain(domain)
                        .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }
}
