package com.cpms.api.setting.model;

import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

import com.cpms.api.setting.dto.request.ReqProjectDTO;
import com.cpms.common.helper.BaseEntity;
import com.cpms.common.helper.YesNo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "cpms_project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CpmsProject extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "company_id", columnDefinition = "int(10) unsigned", nullable = false)
    @Comment("소속 업체 ID")
    private Integer companyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "company_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsCompany cpmsCompany;

    @Column(name = "project_nm", length = 255)
    @Comment("프로젝트 명")
    private String projectNm;

    @Column(name = "project_info", length = 255)
    @Comment("프로젝트 설명")
    private String projectInfo;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('Y','N')", nullable = false)
    @Comment("진행 여부")
    private YesNo progressYn = YesNo.Y;

    public static CpmsProject from(ReqProjectDTO reqDTO, Integer regId) {
        return CpmsProject.builder()
                .companyId(reqDTO.getCompanyId())
                .projectNm(reqDTO.getProjectNm())
                .projectInfo(reqDTO.getProjectInfo())
                .progressYn(YesNo.Y)
                .regId(regId)
                .build();
    }
}
