package com.cpms.api.support.dto.request;

import com.cpms.cmmn.helper.ReqPageDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class ReqSupportListDTO extends ReqPageDTO {

    private Integer searchCompanyId;

    private Integer searchRequestCd;

    private Integer searchStatusCd;

    private String searchStartDt;

    private String searchEndDt;

    private String searchTitle;

    private Integer regId;
}
