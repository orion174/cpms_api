package com.cpms.api.setting.dto.request;

import com.cpms.common.helper.ReqPageDTO;

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
