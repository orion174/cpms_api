package com.cpms.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.cpms.common.helper.YesNo;

public record ReqUserDTO(
        @NotBlank @Size(max = 30, message = "아이디는 30자 이내여야 합니다.") String loginId,
        @NotBlank @Size(max = 30, message = "이름은 30자 이내여야 합니다.") String userNm,
        @NotBlank String authType,
        @NotNull Integer companyId,
        @Pattern(regexp = "^\\d{10,11}$", message = "휴대폰 번호 형식이 올바르지 않습니다.") String userPhone,
        String userEmail,
        String userDept,
        String userPos,
        String userInfo,
        String userNote,
        YesNo useYn) {}
