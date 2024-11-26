package com.cpms.api.user.model;

import jakarta.persistence.*;

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

    @Column(length = 255)
    private String companyNm;
}
