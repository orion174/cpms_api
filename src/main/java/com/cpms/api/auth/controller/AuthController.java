package com.cpms.api.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cpms.api.auth.dto.req.ReqLoginDTO;
import com.cpms.api.auth.dto.req.ReqRefreshTokenDTO;
import com.cpms.api.auth.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(
            HttpServletRequest req, @RequestBody @Valid ReqLoginDTO reqLoginDTO) {

        return authService.userLogin(req, reqLoginDTO);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest req, @RequestBody ReqRefreshTokenDTO reqRefreshTokenDTO) {

        return authService.refreshToken(req, reqRefreshTokenDTO);
    }
}
