package com.cpms.api.setting.service;

import org.springframework.http.ResponseEntity;

import com.cpms.api.setting.dto.request.*;
import com.cpms.common.sms.dto.SmsDTO;

public interface SignUpService {

    ResponseEntity<?> sendSMS(SmsDTO smsDTO);

    ResponseEntity<?> authPhone(ReqAuthADTO reqAuthADTO);
}
