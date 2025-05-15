package com.cpms.api.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.cpms.api.auth.dto.response.ResCookieDTO;

public interface CookieService {

    ResCookieDTO getCookies(HttpServletRequest request);

    void deleteCookies(HttpServletRequest request, HttpServletResponse response);
}
