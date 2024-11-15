package com.cpms.api.user.model;

import jakarta.persistence.*;

import com.cpms.common.helper.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "cpms_project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CpmsProject extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectId;

    @Column(length = 255)
    private String projectNm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsCompany cpmsCompany;
}
