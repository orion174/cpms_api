package com.cpms.api.auth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cpms.api.auth.dto.response.ResCookieDTO;
import com.cpms.api.auth.service.CookieService;
import com.cpms.common.response.ApiResponse;
import com.cpms.common.response.ResponseMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cookie")
public class CookieController {

    private final CookieService cookieService;

    @PostMapping("/get")
    public ResponseEntity<ApiResponse> getCookies(HttpServletRequest request) {
        ResCookieDTO result = cookieService.getCookies(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        result,
                        ResponseMessage.COOKIE_FETCH_SUCCESS.getMessage()));
    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResponse> deleteCookies(
            HttpServletRequest request, HttpServletResponse response) {
        cookieService.deleteCookies(request, response);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.COOKIE_DELETE_SUCCESS.getMessage()));
    }
}
