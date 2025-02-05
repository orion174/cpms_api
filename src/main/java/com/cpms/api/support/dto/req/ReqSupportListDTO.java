package com.cpms.api.support.dto.req;

import com.cpms.common.helper.ReqPageDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class ReqSupportListDTO extends ReqPageDTO {

    private Integer searchCompanyId;    // 검색 회사

    private Integer searchRequestCd;    // 검색 요청유형

    private Integer searchStatusCd;     // 검색 처리상태

    private String searchStartDt;       // 검색 시작일

    private String searchEndDt;         // 검색 마감일

    private String searchTitle;         // 검색 제목
}
