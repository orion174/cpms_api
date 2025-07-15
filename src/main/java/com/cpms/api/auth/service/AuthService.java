package com.cpms.api.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

import com.cpms.api.auth.dto.request.ReqLoginDTO;
import com.cpms.api.auth.dto.request.ReqRefreshTokenDTO;
import com.cpms.api.auth.dto.response.ResLoginDTO;
import com.cpms.api.auth.dto.response.ResRefreshTokenDTO;

public interface AuthService {

    ResponseEntity<ResLoginDTO> userLogin(
            HttpServletRequest request, HttpServletResponse response, ReqLoginDTO reqLoginDTO);

    ResRefreshTokenDTO refreshToken(
            @CookieValue("refreshToken") String refreshToken,
            @RequestBody ReqRefreshTokenDTO reqDto);

    UserDetails loadUserByUsername(String loginId);
}
