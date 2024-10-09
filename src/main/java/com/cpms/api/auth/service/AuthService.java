package com.cpms.api.auth.service;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cpms.api.auth.dto.req.ReqLoginDTO;
import com.cpms.api.auth.dto.res.ResLoginDTO;

public interface AuthService {

    ResLoginDTO userLogin(HttpServletRequest request, ReqLoginDTO reqLoginDTO);

    UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException;
}
