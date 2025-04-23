package com.cpms.api.setting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.api.setting.dto.request.*;
import com.cpms.api.setting.service.SignUpService;
import com.cpms.common.sms.dto.SmsDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/setting/sign-up")
public class SignUpController {

    private final SignUpService signupService;

    @PostMapping("/sendSMS")
    public ResponseEntity<?> sendSMS(@RequestBody SmsDTO smsDTO) {
        return signupService.sendSMS(smsDTO);
    }

    @PostMapping("/authPhone")
    public ResponseEntity<?> authPhone(@RequestBody ReqAuthADTO reqAuthADTO) {
        return signupService.authPhone(reqAuthADTO);
    }
}
