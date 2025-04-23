package com.cpms.api.code.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResCommonCodeDTO {

    private Integer codeId;

    private String codeNm;

    @QueryProjection
    public ResCommonCodeDTO(Integer codeId, String codeNm) {
        this.codeId = codeId;
        this.codeNm = codeNm;
    }
}
