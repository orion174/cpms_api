package com.cpms.api.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResAuthDTO {

    private String authType;

    private String authNm;

    @QueryProjection
    public ResAuthDTO(String authType, String authNm) {
        this.authType = authType;
        this.authNm = authNm;
    }
}
