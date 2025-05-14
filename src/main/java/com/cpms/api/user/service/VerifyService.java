package com.cpms.api.user.service;

import com.cpms.api.user.dto.request.ReqCheckIdDTO;
import com.cpms.api.user.dto.request.ReqRegisterDTO;
import com.cpms.api.user.dto.request.ReqSmsCodeDTO;
import com.cpms.common.sms.dto.SmsDTO;

public interface VerifyService {

    void sendSmsCode(SmsDTO smsDTO);

    void identifyVerifyCode(ReqSmsCodeDTO reqSmsCodeDTO);

    void validateDuplicateLoginId(ReqCheckIdDTO reqCheckIdDTO);

    void registerUser(ReqRegisterDTO reqRegisterDTO);
}
