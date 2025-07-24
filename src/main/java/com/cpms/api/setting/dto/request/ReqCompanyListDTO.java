package com.cpms.api.setting.dto.request;

import com.cpms.common.helper.ReqPageDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqCompanyListDTO extends ReqPageDTO {

    private Integer companyId;

    private String companyNm;

    private String useYn;
}
