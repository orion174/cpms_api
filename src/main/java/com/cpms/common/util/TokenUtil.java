package com.cpms.common.util;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenUtil {

    /**
     * 리프레쉬 토큰과 로그인 히스토리 유효성을 검증한다.
     *
     * @param refreshToken
     * @param loginHistoryId
     * @return
     */
    public static boolean isValidRefreshToken(String refreshToken, int loginHistoryId) {
        return Optional.ofNullable(refreshToken)
                        .filter(token -> !token.isEmpty())
                        .filter(token -> !"null".equalsIgnoreCase(token))
                        .filter(token -> !"undefined".equalsIgnoreCase(token))
                        .isPresent()
                && loginHistoryId > 0;
    }

    /**
     * 토큰 만료 시간을 초 단위로 반환한다.
     *
     * @param expiration
     * @return
     */
    public static int parseExpiration(Object expiration) {
        try {
            if (expiration == null) return 0;
            int exp;
            if (expiration instanceof Number) {
                exp = ((Number) expiration).intValue();
            } else {
                exp = Integer.parseInt(expiration.toString().trim());
            }
            return exp / 1000;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 키 유효성을 검증한다.
     *
     * @param key
     * @return
     */
    public static boolean isValidKey(String key) {
        return Optional.ofNullable(key)
                .filter(k -> !k.isEmpty())
                .filter(k -> !k.contains("adm"))
                .isPresent();
    }
}
