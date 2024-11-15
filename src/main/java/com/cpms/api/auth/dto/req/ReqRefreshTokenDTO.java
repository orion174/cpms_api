package com.cpms.api.auth.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqRefreshTokenDTO {

    private Long loginHistoryId;

    private String nm;

    private String refreshToken;
}