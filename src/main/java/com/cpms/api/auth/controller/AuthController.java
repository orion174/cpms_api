package com.cpms.api.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cpms.api.auth.dto.request.ReqLoginDTO;
import com.cpms.api.auth.dto.request.ReqRefreshTokenDTO;
import com.cpms.api.auth.dto.response.ResLoginDTO;
import com.cpms.api.auth.dto.response.ResRefreshTokenDTO;
import com.cpms.api.auth.service.AuthService;
import com.cpms.cmmn.response.ApiResponse;
import com.cpms.cmmn.response.ResponseMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> userLogin(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody @Valid ReqLoginDTO reqLoginDTO) {

        ResponseEntity<ResLoginDTO> loginResponse =
                authService.userLogin(request, response, reqLoginDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        loginResponse.getBody(),
                        ResponseMessage.LOGIN_SUCCESS.getMessage()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse> refreshToken(
            @CookieValue("refreshToken") String refreshToken,
            @RequestBody ReqRefreshTokenDTO reqRefreshTokenDTO) {

        ResRefreshTokenDTO result = authService.refreshToken(refreshToken, reqRefreshTokenDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        result,
                        ResponseMessage.REFRESH_SUCCESS.getMessage()));
    }
}
