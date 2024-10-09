package com.cpms.api.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
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
}
