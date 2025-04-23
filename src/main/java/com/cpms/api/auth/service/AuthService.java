package com.cpms.api.auth.service;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cpms.api.auth.dto.request.*;
import com.cpms.api.auth.dto.response.*;

public interface AuthService {

    ResLoginDTO userLogin(HttpServletRequest request, ReqLoginDTO reqLoginDTO);

    UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException;

    ResRreshTokenDTO refreshToken(
            HttpServletRequest request, ReqRefreshTokenDTO reqRefreshTokenDTO);
}
