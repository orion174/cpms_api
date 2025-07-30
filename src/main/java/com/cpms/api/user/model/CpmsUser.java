package com.cpms.api.user.model;

import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

import com.cpms.api.setting.model.CpmsCompany;
import com.cpms.api.user.dto.request.ReqRegisterDTO;
import com.cpms.api.user.dto.request.ReqUserDTO;
import com.cpms.cmmn.helper.AuthType;
import com.cpms.cmmn.helper.BaseEntity;
import com.cpms.cmmn.helper.Constants;
import com.cpms.cmmn.helper.YesNo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "company_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsCompany cpmsCompany;

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

    @Column(name = "user_email", length = 255)
    @Comment("사용자 이메일")
    private String userEmail;

    @Column(name = "user_dept", length = 255)
    @Comment("부서")
    private String userDept;

    @Column(name = "user_pos", length = 255)
    @Comment("직급")
    private String userPos;

    @Column(name = "user_info", length = 255)
    @Comment("사용자 정보")
    private String userInfo;

    @Column(name = "user_note", columnDefinition = "TEXT")
    @Comment("기타 메모")
    private String userNote;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('Y','N')", nullable = false)
    @Comment("사용 유무")
    private YesNo useYn = YesNo.Y;

    public static CpmsUser fromRegister(ReqRegisterDTO dto, String password, Integer regId) {
        return CpmsUser.builder()
                .authType(AuthType.TEMP.getCode())
                .companyId(Constants.INIT_COMPANY_ID)
                .loginId(dto.loginId())
                .loginPw(password)
                .userNm(Constants.INIT_USER_NAME)
                .userPhone(dto.phone())
                .useYn(YesNo.Y)
                .regId(regId)
                .build();
    }

    public static CpmsUser fromCreate(ReqUserDTO dto, String password, Integer regId) {
        return CpmsUser.builder()
                .authType(dto.authType())
                .companyId(dto.companyId())
                .loginId(dto.loginId())
                .loginPw(password)
                .userNm(dto.userNm())
                .userPhone(dto.userPhone())
                .userEmail(dto.userEmail())
                .userDept(dto.userDept())
                .userPos(dto.userPos())
                .userInfo(dto.userInfo())
                .userNote(dto.userNote())
                .useYn(YesNo.Y)
                .regId(regId)
                .build();
    }
}
