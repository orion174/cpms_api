package com.cpms.api.setting.dto.request;

import com.cpms.common.helper.YesNo;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqProjectDTO {

    private Integer companyId;

    private String projectNm;

    private String projectInfo;

    private YesNo progressYn;
}
