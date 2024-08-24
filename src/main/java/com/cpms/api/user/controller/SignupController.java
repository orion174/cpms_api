package com.cpms.api.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.api.user.dto.req.*;
import com.cpms.api.user.service.SignupService;
import com.cpms.common.sms.dto.SmsDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/signup")
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/sendSMS")
    public ResponseEntity<?> sendSMS(@RequestBody SmsDTO smsDTO) {
        return signupService.sendSMS(smsDTO);
    }

    @PostMapping("/authPhone")
    public ResponseEntity<?> authPhone(@RequestBody ReqAuthADTO reqAuthADTO) {
        return signupService.authPhone(reqAuthADTO);
    }
}
