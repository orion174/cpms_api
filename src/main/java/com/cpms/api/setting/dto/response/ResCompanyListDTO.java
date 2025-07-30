package com.cpms.api.setting.dto.response;

import java.time.LocalDateTime;

import com.cpms.cmmn.helper.YesNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResCompanyListDTO {

    private Integer companyId;

    private String authType;

    private String companyNm;

    private String address;

    private YesNo useYn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime regDt;

    @QueryProjection
    public ResCompanyListDTO(Integer companyId, String companyNm) {
        this.companyId = companyId;
        this.companyNm = companyNm;
    }
}
