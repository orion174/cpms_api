package com.cpms.api.user.service.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jakarta.annotation.PostConstruct;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpms.api.auth.model.CpmsUser;
import com.cpms.api.auth.repository.CpmsUserRepository;
import com.cpms.api.user.dto.request.ReqCheckIdDTO;
import com.cpms.api.user.dto.request.ReqRegisterDTO;
import com.cpms.api.user.dto.request.ReqSmsCodeDTO;
import com.cpms.api.user.service.VerifyService;
import com.cpms.api.user.vo.AuthVO;
import com.cpms.common.exception.CustomException;
import com.cpms.common.helper.YesNo;
import com.cpms.common.response.ErrorCode;
import com.cpms.common.sms.dto.SmsDTO;
import com.cpms.common.sms.service.SmsService;
import com.cpms.common.util.CommonUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerifyServiceImpl implements VerifyService {

    // 인증코드 유효기간
    private static final long CODE_VALIDITY_DURATION = 5 * 60 * 1000;
    // 인증 유효기간
    private static final long VALIDITY_DURATION = 10 * 60 * 1000;
    // 인증 삭제기간
    private static final long CLEANUP_INTERVAL = 15 * 60 * 1000;
    // 인증 코드
    private Map<String, AuthVO> verificationCodes = new ConcurrentHashMap<>();
    // 인증 여부
    private Map<String, Boolean> verificationStatus = new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final SmsService smsService;

    private final CpmsUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init() {
        scheduler.scheduleAtFixedRate(
                this::cleanUpExpiredCodes, 0, CLEANUP_INTERVAL, TimeUnit.MILLISECONDS);
    }

    // 만료된 인증 삭제
    private void cleanUpExpiredCodes() {
        long currentTime = System.currentTimeMillis();

        Iterator<Map.Entry<String, AuthVO>> iterator = verificationCodes.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, AuthVO> entry = iterator.next();
            AuthVO authVO = entry.getValue();

            if (currentTime - authVO.timestamp() > VALIDITY_DURATION) {
                iterator.remove();
                verificationStatus.remove(entry.getKey());
            }
        }
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    /**
     * 입력된 전화번호로 문자메세지를 보낸다.
     *
     * @param smsDTO
     * @return
     */
    @Override
    public void sendSmsCode(SmsDTO smsDTO) {
        final String PREFIX = "[CPMS] 휴대폰 인증 번호 : ";
        final String originPhone = smsDTO.getReceiver();

        if (originPhone == null || originPhone.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        // 인증번호 생성 및 저장
        final String authCode = CommonUtil.generateAuthCode();
        final long now = System.currentTimeMillis();

        verificationCodes.put(originPhone, new AuthVO(authCode, now));
        verificationStatus.put(originPhone, false);

        // 문자 DTO 재구성 후 전송 요청
        SmsDTO smsRequest =
                SmsDTO.builder().receiver(originPhone).message(PREFIX + authCode).build();

        int result = smsService.sendSMS(smsRequest);

        if (result != 0) {
            throw new CustomException(ErrorCode.SMS_SEND_FAILED);
        }
    }

    /**
     * 문자로 전송된 인증번호와 사용자가 입력한 인증번호가 일치하는지 검사한다.
     *
     * @param reqSmsCodeDTO
     */
    public void identifyVerifyCode(ReqSmsCodeDTO reqSmsCodeDTO) {
        String originPhone = reqSmsCodeDTO.getOriginPhone();
        String inputCode = reqSmsCodeDTO.getCheckCode();

        AuthVO authVO = verificationCodes.get(originPhone);

        // 인증요청이 존재하지 않는다.
        if (authVO == null) {
            throw new CustomException(ErrorCode.AUTH_CODE_NOT_FOUND);
        }

        // 인증만료
        if (System.currentTimeMillis() - authVO.timestamp() > CODE_VALIDITY_DURATION) {
            verificationCodes.remove(originPhone);
            verificationStatus.remove(originPhone);

            throw new CustomException(ErrorCode.AUTH_CODE_EXPIRED);
        }

        // 인증번호 불일치
        if (!authVO.code().equals(inputCode)) {
            throw new CustomException(ErrorCode.AUTH_CODE_MISMATCH);
        }

        // 이미 인증된 요청
        if (Boolean.TRUE.equals(verificationStatus.get(originPhone))) {
            throw new CustomException(ErrorCode.AUTH_ALREADY_VERIFIED);
        }

        verificationStatus.put(originPhone, true);
    }

    /**
     * 아이디 중복 체크
     *
     * @param reqCheckIdDTO
     */
    public void validateDuplicateLoginId(ReqCheckIdDTO reqCheckIdDTO) {
        String loginId = reqCheckIdDTO.getLoginId();

        if (loginId == null || loginId.isBlank()) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        boolean exists = userRepository.existsByLoginId(loginId);

        if (exists) {
            throw new CustomException(ErrorCode.ID_DUPLICATE);
        }
    }

    /**
     * CPMS 임시 회원 가입
     *
     * @param reqRegisterDTO
     */
    @Transactional
    public void registerUser(ReqRegisterDTO reqRegisterDTO) {
        String phone = reqRegisterDTO.phone();
        String loginId = reqRegisterDTO.loginId();

        // 1. 이미 등록된 번호인지 확인
        if (userRepository.existsByUserPhone(phone)) {
            throw new CustomException(ErrorCode.PHONE_ALREADY_REGISTERED);
        }

        // 2. 인증 성공 여부 확인
        Boolean isVerified = verificationStatus.get(phone);
        if (isVerified == null || !isVerified) {
            throw new CustomException(ErrorCode.PHONE_NOT_VERIFIED);
        }

        // 3. 인증된 번호와 제출 번호 일치 여부 확인
        AuthVO authVO = verificationCodes.get(phone);
        if (authVO == null || !phone.equals(phone)) {
            throw new CustomException(ErrorCode.PHONE_VERIFICATION_MISMATCH);
        }

        CpmsUser user =
                CpmsUser.builder()
                        .authType("TEMP")
                        .companyId(0)
                        .loginId(loginId)
                        .loginPw(passwordEncoder.encode(reqRegisterDTO.password()))
                        .userNm(loginId)
                        .userPhone(phone)
                        .useYn(YesNo.Y)
                        .regId(1)
                        .build();

        userRepository.save(user);
    }
}
