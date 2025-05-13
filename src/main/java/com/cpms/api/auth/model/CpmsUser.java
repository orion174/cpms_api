package com.cpms.api.auth.model;

import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

import com.cpms.common.helper.BaseEntity;
import com.cpms.common.helper.YesNo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "cpms_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CpmsUser extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "auth_type", length = 30, nullable = false)
    @Comment("사용자 별 권한")
    private String authType;

    @Column(name = "company_id", columnDefinition = "int(10) unsigned", nullable = false)
    @Comment("사용자 소속 업체 ID")
    private Integer companyId;

    @Column(name = "login_id", length = 255, nullable = false)
    @Comment("로그인 ID")
    private String loginId;

    @Column(name = "login_pw", length = 255, nullable = false)
    @Comment("로그인 비밀번호")
    private String loginPw;

    @Column(name = "user_nm", length = 255)
    @Comment("이름")
    private String userNm;

    @Column(name = "user_phone", length = 255, nullable = false)
    @Comment("전화번호")
    private String userPhone;

    @Column(name = "user_dept", length = 255)
    @Comment("부서")
    private String userDept;

    @Column(name = "user_pos", length = 255)
    @Comment("직급")
    private String userPos;

    @Column(name = "user_info", length = 255)
    @Comment("사용자 정보")
    private String userInfo;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('Y','N')", nullable = false)
    @Comment("사용 유무")
    private YesNo useYn = YesNo.Y;
}
