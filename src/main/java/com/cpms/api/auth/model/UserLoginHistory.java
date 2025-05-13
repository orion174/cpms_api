package com.cpms.api.auth.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_login_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_history_id")
    private Integer loginHistoryId;

    @Column(name = "user_id", columnDefinition = "int(10) unsigned", nullable = false)
    @Comment("사용자 고유 ID")
    private Integer userId;

    @Column(name = "login_id", length = 255, nullable = false)
    @Comment("사용자 로그인 ID")
    private String loginId;

    @Column(name = "access_ip", length = 255, nullable = false)
    @Comment("접속 IP 주소")
    private String accessIp;

    @Column(name = "refresh_token", length = 255, nullable = false)
    @Comment("리프레쉬 토큰")
    private String refreshToken;

    @Column(name = "reg_dt")
    @Comment("생성일자")
    private LocalDateTime regDt;

    @Builder
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
