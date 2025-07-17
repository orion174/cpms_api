package com.cpms.api.setting.dto.response;

import java.time.LocalDateTime;

import com.cpms.common.helper.YesNo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResProjectListDTO {

    private Integer projectId;

    private String companyNm;

    private String projectNm;

    private String projectInfo;

    private YesNo progressYn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime regDt;

    @QueryProjection
    public ResProjectListDTO(Integer projectId, String projectNm) {
        this.projectId = projectId;
        this.projectNm = projectNm;
    }
}
