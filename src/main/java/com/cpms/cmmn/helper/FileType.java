package com.cpms.cmmn.helper;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {
    REQUEST("REQ"),
    RESPONSE("RES");

    private final String code;

    public static FileType fromCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code.equals(code))
                .findFirst()
                .orElse(null);
    }
}
