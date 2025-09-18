package com.cpms.api.code.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqCmmnCodeDTO {
    private String groupCode; // 코드 그룹
    // TODO 공통코드 고도화
}
