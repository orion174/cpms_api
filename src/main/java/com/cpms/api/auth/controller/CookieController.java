package com.cpms.api.auth.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cpms.common.util.CookieUtil;
import com.cpms.common.util.TokenUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cookie")
public class CookieController {

    /**
     * 클라이언트에서 전달된 데이터를 기반으로 쿠키를 생성하고 저장한다.
     *
     * @param res
     * @param params
     * @return
     */
    @PostMapping("/saveCookie")
    public ResponseEntity<?> saveCookie(
            HttpServletResponse response, @RequestBody Map<String, String> params) {
        int refreshTokenExpiration =
                TokenUtil.parseExpiration(params.get("refreshTokenExpiration"));

        int accessTokenExpiration = TokenUtil.parseExpiration(params.get("accessTokenExpiration"));

        CookieUtil.addCookieIfPresent(
                response, "accessToken", params.get("accessToken"), accessTokenExpiration);

        List.of("authType", "loginHistoryId", "companyId", "userId", "refreshToken")
                .forEach(
                        key ->
                                CookieUtil.addCookieIfPresent(
                                        response, key, params.get(key), refreshTokenExpiration));

        return ResponseEntity.ok().build();
    }

    /**
     * 클라이언트로부터 모든 쿠키의 이름과 값을 반환한다.
     *
     * @param request
     * @return
     */
    @PostMapping("/getCookie")
    @ResponseBody
    public ResponseEntity<?> getCookie(HttpServletRequest request) {

        Cookie[] cookies = Optional.ofNullable(request.getCookies()).orElse(new Cookie[] {});

        Map<String, String> result =
                Arrays.stream(cookies).collect(Collectors.toMap(Cookie::getName, Cookie::getValue));

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 쿠키를 삭제한다.
     *
     * @param req
     * @param res
     * @param params
     */
    @PostMapping("/deleteCookie")
    public void deleteCookie(
            HttpServletRequest req,
            HttpServletResponse res,
            @RequestBody Map<String, String> params) {

        String key = params.get("key");

        if (TokenUtil.isValidKey(key)) {
            CookieUtil.deleteCookieIfAllowed(res, key);

        } else {
            Arrays.stream(Optional.ofNullable(req.getCookies()).orElse(new Cookie[] {}))
                    .filter(cookie -> !cookie.getName().contains("adm"))
                    .forEach(cookie -> CookieUtil.deleteCookieIfAllowed(res, cookie.getName()));
        }
    }
}
