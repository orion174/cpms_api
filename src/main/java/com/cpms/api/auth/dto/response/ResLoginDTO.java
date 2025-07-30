package com.cpms.api.auth.dto.response;

import com.cpms.cmmn.helper.YesNo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryProjection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "사용자 로그인 응답 DTO")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResLoginDTO {

    @Schema(description = "사용자 권한")
    private String authType;

    @Schema(description = "로그인 히스토리 ID")
    private Integer loginHistoryId;

    @Schema(description = "사용자 ID")
    private Integer userId;

    @Schema(description = "사용자 명")
    private String userNm;

    @Schema(description = "업체 ID")
    private Integer companyId;

    @Schema(description = "로그인 ID")
    private String loginId;

    @Schema(description = "로그인 비밀번호")
    private String loginPw;

    @Schema(description = "사용 유무")
    private YesNo useYn;

    @Schema(description = "어세스 토큰")
    private String accessToken;

    @Schema(description = "토큰 만료기한")
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
