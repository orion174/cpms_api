package com.cpms.api.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReqSmsCodeDTO {

    private String originPhone;

    private String checkCode;
}
