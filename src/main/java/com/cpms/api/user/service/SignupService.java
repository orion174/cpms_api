package com.cpms.api.user.service;

import org.springframework.http.ResponseEntity;

import com.cpms.api.user.dto.req.*;
import com.cpms.common.sms.dto.SmsDTO;

public interface SignupService {

    ResponseEntity<?> sendSMS(SmsDTO smsDTO);

    ResponseEntity<?> authPhone(ReqAuthADTO reqAuthADTO);
}
