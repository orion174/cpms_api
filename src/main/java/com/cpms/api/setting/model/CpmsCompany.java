package com.cpms.api.setting.model;

import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

import com.cpms.common.helper.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "cpms_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CpmsCompany extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Integer companyId;

    @Column(name = "auth_type", length = 30, nullable = false)
    @Comment("업체 권한")
    private String authType;

    @Column(name = "company_nm", length = 255)
    @Comment("회사 명")
    private String companyNm;
}
