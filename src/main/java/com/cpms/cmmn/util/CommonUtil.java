package com.cpms.cmmn.util;

import java.util.Optional;
import java.util.Random;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {

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
