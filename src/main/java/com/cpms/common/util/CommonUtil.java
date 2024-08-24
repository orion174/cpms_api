package com.cpms.common.util;

import java.util.Random;

public class CommonUtil {

    // 전화번호 형식을 변경하는 메소드
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

    // 마지막 줄바꿈 제거를 위한 함수
    public static String removeLastNewline(String str) {
        return str.endsWith("\n") ? str.substring(0, str.length() - 1) : str;
    }

    public static String convertEmptyToNull(String input) {
        if (input != null && input.trim().isEmpty()) {
            return null;
        }
        return input;
    }

    // 6자리 랜덤 숫자 생성
    public static String generateAuthCode() {
        Random random = new Random();
        StringBuilder authCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            authCode.append(random.nextInt(10));
        }

        return authCode.toString();
    }
}
