package com.cpms.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ReqRegisterDTO(
        @NotBlank String loginId,
        @NotBlank String password,
        @NotBlank String confirmPassword,
        @NotBlank @Pattern(regexp = "^\\d{10,11}$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
                String phone) {}
