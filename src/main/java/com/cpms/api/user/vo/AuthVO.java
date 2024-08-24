package com.cpms.api.user.vo;

import lombok.Value;

@Value
public class AuthVO {
    String code; // 인증번호
    long timestamp; // 시간
}
