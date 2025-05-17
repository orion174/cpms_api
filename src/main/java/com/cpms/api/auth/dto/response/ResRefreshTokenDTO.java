package com.cpms.api.auth.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResRefreshTokenDTO {
    private String accessToken;
    private Integer accessTokenExpiration;
}
