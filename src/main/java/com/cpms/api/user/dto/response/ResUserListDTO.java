package com.cpms.api.user.dto.response;

import com.cpms.cmmn.helper.YesNo;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResUserListDTO {
    private int userId;

    private String authType;

    private String companyNm;

    private String userNm;

    private String userDept;

    private String userPos;

    private String regDt;

    private YesNo useYn;
}
