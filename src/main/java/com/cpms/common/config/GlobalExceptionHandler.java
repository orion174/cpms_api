package com.cpms.common.config;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cpms.common.exception.CustomException;
import com.cpms.common.response.ApiResponse;
import com.cpms.common.response.ErrorCode;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleCustomException(
            CustomException ex, HttpServletRequest request) {
        ErrorCode code = ex.getErrorCode();
        return ResponseEntity.status(code.getHttpStatus())
                .body(
                        ApiResponse.fail(
                                code.getHttpStatus().value(), code.getMessage(), code.getCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAll(Exception ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ApiResponse.fail(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                ErrorCode.INTERNAL_ERROR.getMessage(),
                                ErrorCode.INTERNAL_ERROR.getCode()));
    }
}
