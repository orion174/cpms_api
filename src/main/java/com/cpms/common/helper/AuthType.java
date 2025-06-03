package com.cpms.common.helper;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthType {
    TEMP("TEMP"),
    USER("USER"),
    ADMIN("ADMIN");

    private final String code;

    public static AuthType fromCode(String code) {
        return Arrays.stream(values())
                .filter(authType -> authType.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
