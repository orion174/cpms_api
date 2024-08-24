package com.cpms.common.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsDTO {
    private String receiver; // 수신자 번호
    private String message; // 문자 메세지 내용
}
