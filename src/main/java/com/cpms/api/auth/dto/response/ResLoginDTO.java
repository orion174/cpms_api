package com.cpms.api.auth.dto.response;

import com.cpms.common.helper.YesNo;
import com.querydsl.core.annotations.QueryProjection;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResLoginDTO {

    private String accessToken;

    private String refreshToken;

    private Integer accessTokenExpiration;

    private Integer refreshTokenExpiration;

    private String authType;

    private Integer loginHistoryId;

    private Integer userId;

    private Integer companyId;

    private String loginId;

    private String loginPw;

    private YesNo useYn;

    private String option;

    @QueryProjection
    public ResLoginDTO(
            String authType,
            Integer userId,
            Integer companyId,
            String loginId,
            String loginPw,
            YesNo useYn) {
        this.authType = authType;
        this.userId = userId;
        this.companyId = companyId;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.useYn = useYn;
    }
}
