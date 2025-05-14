package com.cpms.common.sms.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SmsDTO {

    @NotBlank private String receiver; // 수신자 번호

    private String message; // 문자 메세지 내용
}
