package com.cpms.api.auth.model;

import jakarta.persistence.*;

import com.cpms.common.helper.BaseEntity;
import com.cpms.common.helper.YesNo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cpms_user")
public class CpmsUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(length = 30, nullable = false)
    private String authType;

    @Column(columnDefinition = "int(10) unsigned", nullable = false)
    private Integer companyId;

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

    /* 등록자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id")
    private CpmsUser regUser;

    @Column(columnDefinition = "int(10) unsigned")
    protected Integer udtId;

    @Column(columnDefinition = "int(10) unsigned")
    protected Integer delId;

    public CpmsUser(
            Integer userId,
            String authType,
            Integer companyId,
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
