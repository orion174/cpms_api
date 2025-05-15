package com.cpms.common.helper;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtDTO {

    private String grantType;

    private String authType;

    private String loginHistoryId;

    private String userId;

    private String userNm;

    private String companyId;

    private String loginId;

    private String loginPw;

    private String accessToken;

    private String refreshToken;

    private Integer accessTokenExpiration;

    private Integer refreshTokenExpiration;
}
