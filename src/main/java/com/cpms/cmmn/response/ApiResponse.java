package com.cpms.cmmn.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private final boolean success;

    private final Integer status;

    private final Object data;

    private final String message;

    private final String errorCode;

    private ApiResponse(
            boolean success, Integer status, Object data, String message, String errorCode) {
        this.success = success;
        this.status = status;
        this.data = data;
        this.message = message;
        this.errorCode = errorCode;
    }

    public static ApiResponse success(int status, Object data, String message) {
        return new ApiResponse(true, status, data, message, null);
    }

    public static ApiResponse success(int status, String message) {
        return new ApiResponse(true, status, null, message, null);
    }

    public static ApiResponse fail(int status, String message, String errorCode) {
        return new ApiResponse(false, status, null, message, errorCode);
    }
}
