package com.cpms.common.util;

import java.util.Base64;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;
import org.springframework.util.StringUtils;

import com.cpms.common.helper.JwtDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CookieUtil {

    public static void addCookie(
            HttpServletResponse response, String name, String value, int maxAge, String domain) {
        addCookie(response, name, value, maxAge, domain, true, true, "None");
    }

    public static void addCookie(
            HttpServletResponse response,
            String name,
            String value,
            int maxAge,
            String domain,
            boolean httpOnly,
            boolean secure,
            String sameSite) {
        if (!StringUtils.hasText(name) || !StringUtils.hasText(value)) return;

        ResponseCookie cookie =
                ResponseCookie.from(name, value)
                        .httpOnly(httpOnly)
                        .secure(secure)
                        .sameSite(sameSite)
                        .path("/")
                        .maxAge(maxAge)
                        .domain(domain)
                        .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    public static void addCookieIfPresent(
            HttpServletResponse response, String name, String value, int maxAge, String domain) {
        if (StringUtils.hasText(value)) {
            addCookie(response, name, value, maxAge, domain);
        }
    }

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

    public static void saveLoginCookies(
            HttpServletResponse response,
            JwtDTO jwtDTO,
            int accessExp,
            int refreshExp,
            int loginHistoryId,
            String domain) {
        addCookie(response, "refreshToken", jwtDTO.getRefreshToken(), refreshExp, domain);
        addCookie(response, "accessToken", jwtDTO.getAccessToken(), accessExp, domain);
        addCookie(
                response,
                "loginHistoryId",
                encode(String.valueOf(loginHistoryId)),
                refreshExp,
                domain);
    }

    private static String encode(String value) {
        if (!StringUtils.hasText(value)) return "";
        return Base64.getEncoder().encodeToString(value.getBytes());
    }
}
