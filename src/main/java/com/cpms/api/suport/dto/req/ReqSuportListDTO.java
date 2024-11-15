package com.cpms.api.suport.dto.req;

import com.cpms.common.helper.ReqPageDTO;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqSuportListDTO extends ReqPageDTO {

    private String schStartDt; // 검색 시작일

    private String schEndDt; // 검색 마감일

    private Integer schCompanyId; // 검색 회사 키

    private String schRequestCd; // 검색 요청유형

    private String schStatusCd; // 검색 처리상태

    private String schTitle; // 검색 제목
}
