package com.cpms.api.auth.dto.res;

import com.cpms.common.util.YesNo;
import com.querydsl.core.annotations.QueryProjection;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResLoginDTO {

    private Long userId;

    private String authType;

    private int companyId;

    private String loginId;

    private String loginPw;

    private YesNo useYn;

    private Long loginHistoryId;

    private String accessToken;

    private int accessTokenExpiration;

    private String refreshToken;

    private int refreshTokenExpiration;

    private String option;

    @QueryProjection
    public ResLoginDTO(
            Long userId,
            String authType,
            int companyId,
            String loginId,
            String loginPw,
            YesNo useYn) {
        this.userId = userId;
        this.authType = authType;
        this.companyId = companyId;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.useYn = useYn;
    }
}
