package com.cpms.api.support.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

import com.cpms.api.code.model.CommonCode;
import com.cpms.api.setting.model.CpmsCompany;
import com.cpms.api.setting.model.CpmsProject;
import com.cpms.api.user.model.CpmsUser;
import com.cpms.common.helper.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "support_request")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SupportRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "support_request_id")
    private Integer supportRequestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "request_company_id",
            referencedColumnName = "company_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Comment("해당 ID의 업체에게 문의를 요청")
    private CpmsCompany requestCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_company_id",
            referencedColumnName = "company_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Comment("문의를 등록한 사용자의 업체ID")
    private CpmsCompany userCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "request_project_id",
            referencedColumnName = "project_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Comment("문의 프로젝트 ID")
    private CpmsProject requestProject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "response_user_id",
            referencedColumnName = "user_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Comment("해당 문의에 응답한 사용자 ID")
    private CpmsUser responseUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "request_cd",
            referencedColumnName = "code_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Comment("요청 유형 분류 코드")
    private CommonCode requestCd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "status_cd",
            referencedColumnName = "code_id",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Comment("처리 상태 분류 코드")
    private CommonCode statusCd;

    @Column(name = "request_date", nullable = false)
    @Comment("요청 일자")
    private LocalDate requestDate;

    @Column(name = "response_date")
    @Comment("응답 일자")
    private LocalDate responseDate;

    @Column(name = "support_title", length = 255)
    @Comment("문의 제목")
    private String supportTitle;

    @Column(name = "support_editor", columnDefinition = "TEXT")
    @Comment("문의 상세 내용")
    private String supportEditor;

    /* 문의 요청 및 응답 첨부 파일 */
    @OneToMany(mappedBy = "supportRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupportFile> files = new ArrayList<>();

    public SupportRequest(
            CpmsCompany requestCompany,
            CpmsCompany userCompany,
            CpmsProject requestProject,
            CpmsUser responseUser,
            CommonCode requestCd,
            CommonCode statusCd,
            String requestDate,
            LocalDate responseDate,
            String supportTitle,
            String supportEditor,
            Integer regId) {
        this.requestCompany = requestCompany;
        this.userCompany = userCompany;
        this.requestProject = requestProject;
        this.responseUser = responseUser;
        this.requestCd = requestCd;
        this.statusCd = statusCd;
        this.requestDate = LocalDate.parse(requestDate);
        this.responseDate = responseDate;
        this.supportTitle = supportTitle;
        this.supportEditor = supportEditor;
        this.regId = regId;
    }

    // 처리 상태 업데이트
    public void updateStatusCd(CommonCode statusCd) {
        this.statusCd = statusCd;
    }

    // 처리 담당자 업데이트
    public void updateResponseUser(CpmsUser responseUser) {
        this.responseUser = responseUser;
        this.responseDate = LocalDate.now();
    }

    public void addFile(SupportFile file) {
        if (file != null) {
            files.add(file);
            file.setSupportRequest(this);
        }
    }
}
