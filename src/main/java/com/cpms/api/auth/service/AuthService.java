package com.cpms.api.auth.service;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cpms.api.auth.dto.req.*;
import com.cpms.api.auth.dto.res.*;

public interface AuthService {

    ResLoginDTO userLogin(HttpServletRequest request, ReqLoginDTO reqLoginDTO);

    UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException;

    ResponseEntity<?> refreshToken(ReqRefreshTokenDTO reqRefreshTokenDTO);
}
