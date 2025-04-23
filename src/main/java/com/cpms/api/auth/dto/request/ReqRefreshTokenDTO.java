package com.cpms.api.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReqRefreshTokenDTO {

    private Integer loginHistoryId;

    private String refreshToken;
}
