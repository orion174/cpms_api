package com.cpms.api.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.api.auth.dto.request.ReqLoginDTO;
import com.cpms.api.auth.dto.request.ReqRefreshTokenDTO;
import com.cpms.api.auth.dto.response.ResLoginDTO;
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
            HttpServletRequest request, @RequestBody ReqRefreshTokenDTO reqRefreshTokenDTO) {
        ResRreshTokenDTO result = authService.refreshToken(request, reqRefreshTokenDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        result,
                        ResponseMessage.REFRESH_SUCCESS.getMessage()));
    }
}
