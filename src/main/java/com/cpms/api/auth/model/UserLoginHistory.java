package com.cpms.api.auth.model;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_login_history")
public class UserLoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loginHistoryId;

    private Long userId;

    private String loginId;

    private String accessIp;

    private String refreshToken;

    public UserLoginHistory(Long userId, String loginId, String accessIp) {
        this.userId = userId;
        this.loginId = loginId;
        this.accessIp = accessIp;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
