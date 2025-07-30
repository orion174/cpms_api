package com.cpms.cmmn.sms.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cpms.cmmn.exception.CustomException;
import com.cpms.cmmn.response.ErrorCode;
import com.cpms.cmmn.sms.dto.SmsDTO;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
public class SmsService {

    private final DefaultMessageService messageService;

    @Value("${coolsms.sender}")
    private String sender;

    /**
     * 쿨에스엠에스 API 연동
     *
     * @param apiKey
     * @param apiSecret
     */
    public SmsService(
            @Value("${coolsms.api.key}") String apiKey,
            @Value("${coolsms.api.secret}") String apiSecret) {
        this.messageService =
                NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    /**
     * 쿨에스엠에스 : 문자 메세지를 보낸다.
     *
     * @param smsDTO
     * @return
     */
    public int sendSMS(SmsDTO smsDTO) {
        try {
            Message message = new Message();
            message.setFrom(sender);
            message.setTo(smsDTO.getReceiver());
            message.setText(smsDTO.getMessage());

            SingleMessageSentResponse response =
                    messageService.sendOne(new SingleMessageSendingRequest(message));

            // API 성공 코드가 2000이 아닌 경우 실패로 간주
            if (!"2000".equals(response.getStatusCode())) {
                throw new CustomException(ErrorCode.SMS_SEND_FAILED);
            }

            return 0;

        } catch (Exception e) {
            throw new CustomException(ErrorCode.SMS_SEND_FAILED);
        }
    }
}
