package com.cpms.api.setting.vo;

import lombok.Value;

@Value
public class AuthVO {

    String code; // 인증번호

    long timestamp; // 시간
}
