package com.cpms.common.util;

import java.util.Optional;
import java.util.Random;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseCookie;
import org.springframework.web.multipart.MultipartFile;

public class CommonUtil {

    /*
     * 클라이언트 서버에 쿠키 저장
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        ResponseCookie cookie;

        if (maxAge > 0) {
            cookie =
                    ResponseCookie.from(name, value)
                            .path("/")
                            .maxAge(maxAge) // 쿠키의 유효 기간
                            .httpOnly(true) // JavaScript로 접근할 수 없게 설정 이를 통해 XSS(크로스 사이트 스크립팅) 방지
                            .secure(true) // 쿠키가 HTTPS를 통해서만 전송되도록 설정
                            .sameSite("Strict") // 타 도메인에서 쿠키를 전송할 수 없도록, CSRF(크로스 사이트 요청 위조) 공격을 방지
                            .build();

        } else {
            cookie =
                    ResponseCookie.from(name, value)
                            .path("/")
                            .httpOnly(true)
                            .secure(true)
                            .sameSite("Strict")
                            .build();
        }

        response.addHeader("Set-Cookie", cookie.toString());
    }

    /*
     * 전화번호 형식을 변경하는 메소드
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

    /*
     * 6자리 랜덤 변수 생성
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

    public static boolean hasFiles(MultipartFile[] file) {
        return Optional.ofNullable(file).map(files -> files.length > 0).orElse(false);
    }
}
