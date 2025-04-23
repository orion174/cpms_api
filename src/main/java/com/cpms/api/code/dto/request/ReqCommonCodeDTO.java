package com.cpms.api.code.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqCommonCodeDTO {

    private String groupCode; // 코드 그룹
}
