package com.cpms.api.code.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResComCodeDTO {

    // 공통 코드
    private Integer codeId;
    // 공통 코드 명
    private String codeNm;

    @QueryProjection
    public ResComCodeDTO(Integer codeId, String codeNm) {
        this.codeId = codeId;
        this.codeNm = codeNm;
    }
}
