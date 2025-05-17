package com.cpms.api.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {
    void deleteCookies(HttpServletRequest request, HttpServletResponse response);
}
