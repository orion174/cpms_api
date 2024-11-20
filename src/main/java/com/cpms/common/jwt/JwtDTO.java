package com.cpms.common.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtDTO {

    private String grantType;

    private String accessToken;

    private int accessTokenExpiration;

    private String refreshToken;

    private int refreshTokenExpiration;

    private Integer userId;

    private String authType;

    private int companyId;

    private String loginId;

    private String loginPw;

    private Integer loginHistoryId;
}
