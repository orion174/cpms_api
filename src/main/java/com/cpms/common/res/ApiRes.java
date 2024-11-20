package com.cpms.common.res;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiRes {

    private boolean status = true;

    private Object result;

    private String message;

    public ApiRes(Object result) {
        this.result = result;
    }

    public ApiRes(Object result, String message) {
        this.message = message;
        this.result = result;
    }
}
