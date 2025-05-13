package com.cpms.api.setting.model;

import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

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
    @Column(name = "project_id")
    private Integer projectId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsCompany cpmsCompany;

    @Column(name = "project_nm", length = 255)
    @Comment("프로젝트 명")
    private String projectNm;
}
