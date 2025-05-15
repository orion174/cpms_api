package com.cpms.api.auth.dto.response;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResCookieDTO {
    private Map<String, String> cookies;
}
