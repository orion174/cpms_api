package com.cpms.common.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionCode {
    FAIL_DATA_NOT_FOUND("004", "데이터 없음", "요청한 데이터를 찾을 수 없습니다."),
    ;

    private final String resultCode;

    private final String resultMsg;

    private final String description;
}
