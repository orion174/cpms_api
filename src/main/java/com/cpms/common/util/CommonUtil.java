package com.cpms.common.util;

import java.util.Optional;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {

    /**
     * 전화번호 형식을 변경하는 메소드
     *
     * @param phoneNumber
     * @return
     */
    public static String formatPhoneNumber(String phoneNumber) {
        String formattedNumber = null;
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            String cleanNumber = phoneNumber.replaceAll("-", "");
            // 10자리인 경우 (01012345678)
            if (cleanNumber.length() == 10) {
                formattedNumber = cleanNumber.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
            }
            // 11자리인 경우 (0212345678 혹은 010-1234-5678)
            else if (cleanNumber.length() == 11) {
                formattedNumber = cleanNumber.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
            }
            // 그 외의 경우는 유효하지 않은 전화번호로 간주하고 null 반환
        }
        return formattedNumber;
    }

    /**
     * 6자리 랜덤 변수 생성
     *
     * @return
     */
    public static String generateAuthCode() {
        Random random = new Random();
        StringBuilder authCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            authCode.append(random.nextInt(10));
        }

        return authCode.toString();
    }

    /**
     * 파일 공백체크
     *
     * @param file
     * @return
     */
    public static boolean hasFiles(MultipartFile[] file) {
        return Optional.ofNullable(file).map(files -> files.length > 0).orElse(false);
    }

    /**
     * String을 안전하게 Integer로 변환
     *
     * @param value
     * @return
     */
    public static Integer parseToIntSafely(String value) {
        return Optional.ofNullable(value)
                .map(
                        v -> {
                            try {
                                return (int) Double.parseDouble(v);

                            } catch (NumberFormatException e) {
                                log.warn("String -> Integer 변환 실패", value, e);
                                return null;
                            }
                        })
                .orElse(null);
    }
}
