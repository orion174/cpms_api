package com.cpms.api.auth.model;

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

    private String authType;

    private int companyId;

    private String loginId;

    private String loginPw;

    private String userNm;

    private String userPhone;

    private String userDept;

    private String userPos;

    private String userInfo;

    @Enumerated(EnumType.STRING)
    private YesNo useYn;

    @Enumerated(EnumType.STRING)
    private YesNo delYn;

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
