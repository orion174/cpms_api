package com.cpms.api.auth.dto.response;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResRreshTokenDTO {

    private String accessToken;

    private Integer accessTokenExpiration;
}
