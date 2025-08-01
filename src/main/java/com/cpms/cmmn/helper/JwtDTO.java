package com.cpms.cmmn.helper;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtDTO {
    private String grantType;

    private String authType;

    private Integer loginHistoryId;

    private Integer userId;

    private String userNm;

    private Integer companyId;

    private String loginId;

    private String loginPw;

    private String accessToken;

    private String refreshToken;

    private Integer accessTokenExpiration;

    private Integer refreshTokenExpiration;
}
