package com.cpms.api.setting.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResCompanyListDTO {

    private Integer companyId;

    private String companyNm;

    @QueryProjection
    public ResCompanyListDTO(Integer companyId, String companyNm) {
        this.companyId = companyId;
        this.companyNm = companyNm;
    }
}
