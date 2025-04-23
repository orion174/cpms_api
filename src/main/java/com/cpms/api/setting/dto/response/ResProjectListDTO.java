package com.cpms.api.setting.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResProjectListDTO {

    private Integer projectId;

    private String projectNm;

    @QueryProjection
    public ResProjectListDTO(Integer projectId, String projectNm) {
        this.projectId = projectId;
        this.projectNm = projectNm;
    }
}
