package com.cpms.cmmn.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CookieUtil {

    /**
     * 리프레쉬 토큰을 쿠키에 저장한다.
     *
     * @param req
     * @param res
     * @param refreshToken
     * @param maxAge
     */
    public static void saveRefreshCookie(
            HttpServletRequest req, HttpServletResponse res, String refreshToken, int maxAge) {
        // 클라이언트 브라우저에 리프레쉬 토큰 발급
        ResponseCookie.ResponseCookieBuilder cookieBuilder =
                ResponseCookie.from("refreshToken", refreshToken)
                        .httpOnly(true)
                        .sameSite("None")
                        .path("/")
                        .maxAge(maxAge);

        cookieBuilder.secure(true);
        ResponseCookie cookie = cookieBuilder.build();

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
