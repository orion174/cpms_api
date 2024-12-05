package com.cpms.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CookieUtil {

    /**
     * 새로운 쿠키 생성 후, HTTP 응답 헤더에 추가한다.
     *
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void addCookie(
            HttpServletResponse response, String name, String value, int maxAge) {
        ResponseCookie.ResponseCookieBuilder cookieBuilder =
                ResponseCookie.from(name, value)
                        .path("/")
                        .httpOnly(true)
                        .secure(true)
                        .sameSite("Strict");

        if (maxAge > 0) {
            cookieBuilder.maxAge(maxAge);
        }

        ResponseCookie cookie = cookieBuilder.build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    /**
     * 쿠키를 추가한다.
     *
     * @param response
     * @param value
     * @param name
     * @param maxAge
     */
    public static void addCookieIfPresent(
            HttpServletResponse response, String name, String value, int maxAge) {
        if (value != null) {
            new CookieUtil().addCookie(response, name, value, maxAge);
        }
    }

    /**
     * 쿠키를 삭제한다.
     *
     * @param response
     * @param key
     */
    public static void deleteCookieIfAllowed(HttpServletResponse response, String key) {
        if (TokenUtil.isValidKey(key)) {
            Cookie cookie = new Cookie(key, null);
            cookie.setMaxAge(0);

            response.addCookie(cookie);
        }
    }
}
