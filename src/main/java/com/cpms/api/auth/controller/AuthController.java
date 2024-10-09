package com.cpms.api.auth.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.api.auth.dto.req.ReqLoginDTO;
import com.cpms.api.auth.service.AuthService;
import com.cpms.common.res.ApiRes;
import com.cpms.common.util.CommonUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(
            HttpServletRequest request, @RequestBody @Valid ReqLoginDTO reqLoginDTO) {

        return new ResponseEntity<>(
                new ApiRes(authService.userLogin(request, reqLoginDTO)), HttpStatus.OK);
    }

    @PostMapping("/saveCookie")
    public ResponseEntity<Void> saveCookie(
            HttpServletRequest req,
            HttpServletResponse res,
            @RequestBody Map<String, String> params) {
        CommonUtil commonUtil = new CommonUtil();

        String accessToken = params.get("accessToken");
        String loginStayYn = params.get("loginStayYn");

        if (accessToken != null) {
            int accessTokenExpiration =
                    Integer.parseInt(params.get("accessTokenExpiration")) / 1000;
            commonUtil.addCookie(res, "accessToken", accessToken, accessTokenExpiration);
        }

        String authType = params.get("authType");
        String loginHistoryId = params.get("loginHistoryId");
        String refreshToken = params.get("refreshToken");
        String ipSecurity = params.get("ipSecurity");

        int refreshTokenExpiration = Integer.parseInt(params.get("refreshTokenExpiration")) / 1000;

        /* 로그인 상태 유지 관련 */
        if (loginStayYn != null) {
            if (loginStayYn.equals("Y")) {
                // 로그인 상태 유지
                commonUtil.addCookie(res, "loginStayYn", loginStayYn, refreshTokenExpiration);

            } else {
                // 로그인 상태 유지 X  >>  유효기간을 주지 않음으로써 브라우저 종료시 해당 쿠키가 삭제되게 함
                commonUtil.addCookie(res, "loginStayYn", loginStayYn, 0);
            }
        }

        if (authType != null) {
            commonUtil.addCookie(res, "authType", authType, refreshTokenExpiration);
        }

        if (loginHistoryId != null) {
            commonUtil.addCookie(res, "loginHistoryId", loginHistoryId, refreshTokenExpiration);
        }

        if (refreshToken != null) {
            commonUtil.addCookie(res, "refreshToken", refreshToken, refreshTokenExpiration);
        }

        if (ipSecurity != null) {
            commonUtil.addCookie(res, "ipSecurity", ipSecurity, refreshTokenExpiration);
        }

        return ResponseEntity.ok().build();
    }
}
