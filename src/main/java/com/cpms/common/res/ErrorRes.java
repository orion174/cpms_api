package com.cpms.common.res;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorRes {

    private boolean status = false;

    private String message;

    private String description;

    public ErrorRes(String message) {
        this.message = message;
    }

    public ErrorRes(String message, String description) {
        this.message = message;
        this.description = description;
    }
}
