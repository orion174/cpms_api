package com.cpms.api.suport.dto.req;

import com.cpms.common.helper.ReqPageDTO;
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
public class ReqSuportListDTO extends ReqPageDTO {

    private Integer schCompanyId; // 검색 회사 키

    private Integer schRequestCd; // 검색 요청유형

    private Integer schStatusCd; // 검색 처리상태

    private String schStartDt; // 검색 시작일

    private String schEndDt; // 검색 마감일

    private String schTitle; // 검색 제목
}
