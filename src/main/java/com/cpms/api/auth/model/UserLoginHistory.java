package com.cpms.api.auth.model;

import java.time.LocalDateTime;

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
    private Integer loginHistoryId;

    @Column private Integer userId;

    @Column private String loginId;

    @Column private String accessIp;

    @Column private String refreshToken;

    @Column private LocalDateTime regDt;

    public UserLoginHistory(Integer userId, String loginId, String accessIp) {
        this.userId = userId;
        this.loginId = loginId;
        this.accessIp = accessIp;
        this.regDt = LocalDateTime.now();
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
