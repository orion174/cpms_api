package com.cpms.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private final boolean success;

    private final Object data;

    private final String message;

    private final String errorCode;

    private ApiResponse(boolean success, Object data, String message, String errorCode) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.errorCode = errorCode;
    }

    public static ApiResponse success(Object data, String message) {
        return new ApiResponse(true, data, message, null);
    }

    public static ApiResponse success(Object data) {
        return new ApiResponse(true, data, null, null);
    }

    public static ApiResponse fail(String message, String errorCode) {
        return new ApiResponse(false, null, message, errorCode);
    }
}
