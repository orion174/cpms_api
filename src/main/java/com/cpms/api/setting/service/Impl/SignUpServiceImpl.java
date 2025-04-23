package com.cpms.api.setting.service.Impl;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jakarta.annotation.PostConstruct;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpms.api.setting.dto.request.ReqAuthADTO;
import com.cpms.api.setting.service.SignUpService;
import com.cpms.api.setting.vo.AuthVO;
import com.cpms.common.response.ApiResponse;
import com.cpms.common.response.ResponseMessage;
import com.cpms.common.sms.dto.SmsDTO;
import com.cpms.common.sms.service.SmsService;
import com.cpms.common.util.CommonUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    private static final long CODE_VALIDITY_DURATION = 5 * 60 * 1000; // 본인인증 코드 유효기간
    private static final long VALIDITY_DURATION =
            10 * 60 * 1000; // 본인인증 유효기간 (완료되었어도 시간지나면 인증 값 삭제)
    private static final long CLEANUP_INTERVAL = 15 * 60 * 1000; // 본인인증 데이터 삭제

    private Map<String, AuthVO> verificationCodes = new ConcurrentHashMap<>(); // 본인인증 코드 데이터
    private Map<String, Boolean> verificationStatus = new ConcurrentHashMap<>(); // 본인인증 여부 데이터

    private final SmsService smsService;

    // 본인인증 삭제 스케쥴려 스레드 할당
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    // 본인인증 삭제 스케쥴러
    @PostConstruct
    private void init() {
        scheduler.scheduleAtFixedRate(
                this::cleanupExpiredCodes, 0, CLEANUP_INTERVAL, TimeUnit.MILLISECONDS);
    }

    // 본인인증 정보 삭제
    private void cleanupExpiredCodes() {
        long currentTime = System.currentTimeMillis();
        Iterator<Map.Entry<String, AuthVO>> iterator = verificationCodes.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, AuthVO> entry = iterator.next();
            AuthVO authVO = entry.getValue();

            if (currentTime - authVO.getTimestamp() > VALIDITY_DURATION) {
                iterator.remove();
                verificationStatus.remove(entry.getKey());
            }
        }
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    // 본인인증 요청
    @Override
    public ResponseEntity<?> sendSMS(SmsDTO smsDTO) {
        String authCode = CommonUtil.generateAuthCode(); // 본인인증 랜덤 6자리 숫자
        String message = "본인인증 번호 : " + authCode;
        String originPhone = smsDTO.getReceiver();
        long currentTime = System.currentTimeMillis();

        // 인증번호 value 생성 후 Map 저장
        AuthVO authVO = new AuthVO(authCode, currentTime);
        // originPhone 요청 원번호 별 vo객체생성
        verificationCodes.put(originPhone, authVO);
        // 인증상태 false로 초기화
        verificationStatus.put(originPhone, false);

        // DTO 세팅
        smsDTO.setMessage(message);

        /*
         * SMS 전송
         * -1 : 실패, 0 : 성공
         */
        int result = smsService.sendSMS(smsDTO);

        return ResponseEntity.ok(
                ApiResponse.success(result, ResponseMessage.TEMP_MESSAGE.getMessage()));
    }

    // 본인인증 확인
    @Override
    public ResponseEntity<?> authPhone(ReqAuthADTO reqAuthADTO) {
        String originPhone = reqAuthADTO.getOriginPhone(); // 사용자 인증요청 전화번호
        String authCheckCode = reqAuthADTO.getAuthCheckCode(); // 사용자 입력 본인인증 번호 6자리

        AuthVO authVO = verificationCodes.get(originPhone);
        boolean isVerified = false;

        int result;
        if (authVO != null) {
            long currentTime = System.currentTimeMillis();
            // 제한 시간 내
            if (currentTime - authVO.getTimestamp() <= CODE_VALIDITY_DURATION) {
                // 사용자 입력 본인인증 번호 6자리와 VO에 저장된 인증번호 비교
                if (authCheckCode.equals(authVO.getCode())) {
                    isVerified = true;
                    verificationStatus.put(originPhone, isVerified);
                    result = 1; // 본인인증 번호 일치
                } else {
                    result = -1; // 본인인증 번호 불일치
                }
            } else {
                verificationCodes.remove(originPhone); // 유효 기간이 지나면 삭제
                result = 0; // 인증번호 유효기간 완료
            }
        } else {
            result = 0; // 인증번호 존재 하지않음
        }

        return ResponseEntity.ok(
                ApiResponse.success(result, ResponseMessage.TEMP_MESSAGE.getMessage()));
    }
}
