package com.cpms.api.user.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.api.user.dto.request.ReqCheckIdDTO;
import com.cpms.api.user.dto.request.ReqRegisterDTO;
import com.cpms.api.user.dto.request.ReqSmsCodeDTO;
import com.cpms.api.user.dto.response.ResCheckIdDTO;
import com.cpms.api.user.service.VerifyService;
import com.cpms.common.response.ApiResponse;
import com.cpms.common.response.ResponseMessage;
import com.cpms.common.sms.dto.SmsDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/verify")
public class VerifyController {

    private final VerifyService verifyService;

    @PostMapping("/send-sms")
    public ResponseEntity<ApiResponse> sendSmsCode(@RequestBody @Valid SmsDTO smsDTO) {
        verifyService.sendSmsCode(smsDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.SEND_SMS_SUCCESS.getMessage()));
    }

    @PostMapping("/identity-code")
    public ResponseEntity<ApiResponse> identifyVerifyCode(
            @RequestBody ReqSmsCodeDTO reqSmsCodeDTO) {
        verifyService.identifyVerifyCode(reqSmsCodeDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.PHONE_AUTH_SUCCESS.getMessage()));
    }

    @PostMapping("/id-check")
    public ResponseEntity<ApiResponse> idCheck(@RequestBody ReqCheckIdDTO reqCheckIdDTO) {
        verifyService.validateDuplicateLoginId(reqCheckIdDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        new ResCheckIdDTO(reqCheckIdDTO.getLoginId()),
                        ResponseMessage.ID_CHECK_SUCCESS.getMessage()));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid ReqRegisterDTO reqRegisterDTO) {
        verifyService.registerUser(reqRegisterDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.REGISTER_SUCCESS.getMessage()));
    }
}
