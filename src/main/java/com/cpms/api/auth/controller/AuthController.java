package com.cpms.api.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.api.auth.dto.request.ReqLoginDTO;
import com.cpms.api.auth.dto.request.ReqRefreshTokenDTO;
import com.cpms.api.auth.dto.response.ResRreshTokenDTO;
import com.cpms.api.auth.service.AuthService;
import com.cpms.common.response.ApiResponse;
import com.cpms.common.response.ResponseMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> userLogin(
            HttpServletRequest request, @RequestBody @Valid ReqLoginDTO reqLoginDTO) {
        Object result = authService.userLogin(request, reqLoginDTO);

        return ResponseEntity.ok(
                ApiResponse.success(result, ResponseMessage.LOGIN_SUCCESS.getMessage()));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse> refreshToken(
            HttpServletRequest request, @RequestBody ReqRefreshTokenDTO reqRefreshTokenDTO) {
        ResRreshTokenDTO result = authService.refreshToken(request, reqRefreshTokenDTO);

        return ResponseEntity.ok(
                ApiResponse.success(result, ResponseMessage.REFRESH_SUCCESS.getMessage()));
    }
}
