package com.cpms.common.config;

import com.cpms.common.res.ErrorRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRes> handleAllExceptions(Exception e, WebRequest req) {

        ErrorRes errorResponse = new ErrorRes(e.getMessage(), req.getDescription(false));

        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
}