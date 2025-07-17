package com.cpms.api.user.service.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.*;

import jakarta.annotation.PostConstruct;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpms.api.user.dto.request.ReqCheckIdDTO;
import com.cpms.api.user.dto.request.ReqRegisterDTO;
import com.cpms.api.user.dto.request.ReqSmsCodeDTO;
import com.cpms.api.user.model.CpmsUser;
import com.cpms.api.user.repository.CpmsUserRepository;
import com.cpms.api.user.service.VerifyService;
import com.cpms.common.exception.CustomException;
import com.cpms.common.response.ErrorCode;
import com.cpms.common.sms.dto.SmsDTO;
import com.cpms.common.sms.service.SmsService;
import com.cpms.common.util.CommonUtil;
import com.cpms.common.util.JwtUserUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerifyServiceImpl implements VerifyService {
    private static final long CODE_VALIDITY_DURATION = 5 * 60 * 1000;
    private static final long VALIDITY_DURATION = 10 * 60 * 1000;
    private static final long CLEANUP_INTERVAL = 15 * 60 * 1000;

    private final Map<String, AuthCodeInfo> authCodeMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final SmsService smsService;
    private final CpmsUserRepository userRepository;
    private final JwtUserUtil jwtUserUtil;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init() {
        scheduler.scheduleAtFixedRate(
                this::cleanUpExpiredCodes, 0, CLEANUP_INTERVAL, TimeUnit.MILLISECONDS);
    }

    private void cleanUpExpiredCodes() {
        long now = System.currentTimeMillis();
        Iterator<Map.Entry<String, AuthCodeInfo>> iterator = authCodeMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, AuthCodeInfo> entry = iterator.next();
            if (now - entry.getValue().getTimestamp() > VALIDITY_DURATION) {
                iterator.remove();
            }
        }
    }

    @Override
    public void sendSmsCode(SmsDTO smsDTO) {
        String phone = smsDTO.getReceiver();
        if (phone == null || phone.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        String code = CommonUtil.generateAuthCode();
        long timestamp = System.currentTimeMillis();

        authCodeMap.put(phone, new AuthCodeInfo(code, timestamp));

        SmsDTO smsRequest =
                SmsDTO.builder().receiver(phone).message("[CPMS] 휴대폰 인증 번호 : " + code).build();

        int result = smsService.sendSMS(smsRequest);
        if (result != 0) {
            throw new CustomException(ErrorCode.SMS_SEND_FAILED);
        }
    }

    @Override
    public void identifyVerifyCode(ReqSmsCodeDTO reqSmsCodeDTO) {
        String phone = reqSmsCodeDTO.getOriginPhone();
        String inputCode = reqSmsCodeDTO.getCheckCode();

        AuthCodeInfo auth = authCodeMap.get(phone);
        if (auth == null) {
            throw new CustomException(ErrorCode.AUTH_CODE_NOT_FOUND);
        }

        if (System.currentTimeMillis() - auth.getTimestamp() > CODE_VALIDITY_DURATION) {
            authCodeMap.remove(phone);
            throw new CustomException(ErrorCode.AUTH_CODE_EXPIRED);
        }

        if (!auth.getCode().equals(inputCode)) {
            throw new CustomException(ErrorCode.AUTH_CODE_MISMATCH);
        }

        if (auth.isVerified()) {
            throw new CustomException(ErrorCode.AUTH_ALREADY_VERIFIED);
        }

        auth.verify(); // 인증 완료 상태 변경
    }

    @Override
    public void validateDuplicateLoginId(ReqCheckIdDTO reqCheckIdDTO) {
        String loginId = reqCheckIdDTO.getLoginId();
        if (loginId == null || loginId.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        if (userRepository.existsByLoginId(loginId)) {
            throw new CustomException(ErrorCode.ID_DUPLICATE);
        }
    }

    @Override
    @Transactional
    public void registerUser(ReqRegisterDTO reqRegisterDTO) {
        String phone = reqRegisterDTO.phone();

        if (userRepository.existsByUserPhone(phone)) {
            throw new CustomException(ErrorCode.PHONE_ALREADY_REGISTERED);
        }

        AuthCodeInfo auth = authCodeMap.get(phone);
        if (auth == null || !auth.isVerified()) {
            throw new CustomException(ErrorCode.PHONE_NOT_VERIFIED);
        }

        CpmsUser user =
                CpmsUser.fromRegister(
                        reqRegisterDTO,
                        passwordEncoder.encode(reqRegisterDTO.password()),
                        jwtUserUtil.getUserId());

        userRepository.save(user);
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    private static class AuthCodeInfo {
        private final String code;
        private final long timestamp;
        private boolean verified;

        public AuthCodeInfo(String code, long timestamp) {
            this.code = code;
            this.timestamp = timestamp;
            this.verified = false;
        }

        public String getCode() {
            return code;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public boolean isVerified() {
            return verified;
        }

        public void verify() {
            this.verified = true;
        }
    }
}
