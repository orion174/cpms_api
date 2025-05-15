package com.cpms.api.auth.dto.response;

import com.cpms.common.helper.YesNo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResLoginDTO {
    private String authType;

    private Integer loginHistoryId;

    private Integer userId;

    private String userNm;

    private Integer companyId;

    private String loginId;

    private String loginPw;

    private YesNo useYn;

    private String accessToken;

    private Integer accessTokenExpiration;

    @QueryProjection
    public ResLoginDTO(
            String authType,
            Integer userId,
            String userNm,
            Integer companyId,
            String loginId,
            String loginPw,
            YesNo useYn) {
        this.authType = authType;
        this.userId = userId;
        this.userNm = userNm;
        this.companyId = companyId;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.useYn = useYn;
    }
}
