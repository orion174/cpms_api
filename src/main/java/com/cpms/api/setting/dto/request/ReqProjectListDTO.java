package com.cpms.api.setting.dto.request;

import com.cpms.cmmn.helper.ReqPageDTO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReqProjectListDTO extends ReqPageDTO {

    private Integer companyId;

    private String progressYn;

    private String projectNm;
}
