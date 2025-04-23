package com.cpms.api.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReqLoginDTO {

    @NotBlank private String loginId;

    @NotBlank private String loginPw;
}
