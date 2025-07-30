package com.cpms.api.setting.dto.request;

import com.cpms.cmmn.helper.YesNo;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqCompanyDTO {

    private Integer companyId;

    private String authType;

    private String companyNm;

    private String zipCode;

    private String address;

    private String extraAddress;

    private String homepage;

    private String companyInfo;

    private String adminNote;

    private YesNo useYn;
}
