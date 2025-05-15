package com.cpms.api.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cpms.api.auth.dto.request.*;
import com.cpms.api.auth.dto.response.*;

public interface AuthService {

    ResponseEntity<ResLoginDTO> userLogin(
            HttpServletRequest request, HttpServletResponse response, ReqLoginDTO reqLoginDTO);

    UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException;

    ResRreshTokenDTO refreshToken(
            HttpServletRequest request, ReqRefreshTokenDTO reqRefreshTokenDTO);
}
