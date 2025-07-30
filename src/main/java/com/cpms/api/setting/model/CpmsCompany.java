package com.cpms.api.setting.model;

import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

import com.cpms.api.setting.dto.request.ReqCompanyDTO;
import com.cpms.cmmn.helper.BaseEntity;
import com.cpms.cmmn.helper.YesNo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "cpms_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CpmsCompany extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "auth_type", length = 30, nullable = false)
    @Comment("회사 등급")
    private String authType;

    @Column(name = "company_nm", length = 255)
    @Comment("회사 명")
    private String companyNm;

    @Column(name = "zip_code", length = 255)
    @Comment("회사 우편번호")
    private String zipCode;

    @Column(name = "address", length = 255)
    @Comment("회사 주소")
    private String address;

    @Column(name = "extra_address", length = 255)
    @Comment("추가 주소 정보")
    private String extraAddress;

    @Column(name = "homepage", length = 255)
    @Comment("홈페이지")
    private String homepage;

    @Column(name = "company_info", columnDefinition = "TEXT")
    @Comment("회사 정보")
    private String companyInfo;

    @Column(name = "admin_note", columnDefinition = "TEXT")
    @Comment("관리자 메모")
    private String adminNote;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('Y','N')", nullable = false)
    @Comment("시스템 사용 여부")
    private YesNo useYn = YesNo.Y;

    public static CpmsCompany from(ReqCompanyDTO dto, Integer regId) {
        return CpmsCompany.builder()
                .authType(dto.getAuthType())
                .companyNm(dto.getCompanyNm())
                .zipCode(dto.getZipCode())
                .address(dto.getAddress())
                .extraAddress(dto.getExtraAddress())
                .homepage(dto.getHomepage())
                .companyInfo(dto.getCompanyInfo())
                .adminNote(dto.getAdminNote())
                .useYn(YesNo.Y)
                .regId(regId)
                .build();
    }
}
