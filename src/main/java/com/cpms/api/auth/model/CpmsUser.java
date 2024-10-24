package com.cpms.api.auth.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.cpms.common.util.YesNo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cpms_user")
public class CpmsUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 30, nullable = false)
    private String authType;

    @Column(columnDefinition = "int(10) unsigned", nullable = false)
    private int companyId;

    @Column(length = 30)
    private String loginId;

    @Column(length = 255)
    private String loginPw;

    @Column(length = 30)
    private String userNm;

    @Column(length = 30)
    private String userPhone;

    @Column(length = 30)
    private String userDept;

    @Column(length = 30)
    private String userPos;

    @Column(length = 255)
    private String userInfo;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('Y','N')", nullable = false)
    private YesNo useYn = YesNo.Y;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('Y','N')", nullable = false)
    private YesNo delYn = YesNo.N;

    @Column(columnDefinition = "int(10) unsigned")
    private Integer regId;

    @Column private LocalDateTime regDt;

    @Column(columnDefinition = "int(10)")
    private Integer udtId;

    @Column private LocalDateTime udtDt;

    @Column(columnDefinition = "int(10) unsigned")
    private Integer delId;

    @Column private LocalDateTime delDt;

    public CpmsUser(
            Long userId,
            String authType,
            int companyId,
            String loginId,
            String userNm,
            String userPhone,
            String userDept,
            String userPos,
            String userInfo,
            YesNo useYn,
            YesNo delYn) {
        this.userId = userId;
        this.authType = authType;
        this.companyId = companyId;
        this.loginId = loginId;
        this.userNm = userNm;
        this.userPhone = userPhone;
        this.userDept = userDept;
        this.userPos = userPos;
        this.userInfo = userInfo;
        this.useYn = useYn;
        this.delYn = delYn;
    }
}
