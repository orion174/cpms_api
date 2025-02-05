package com.cpms.api.support.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.cpms.api.auth.model.CpmsUser;
import com.cpms.api.code.model.ComCode;
import com.cpms.api.user.model.CpmsCompany;
import com.cpms.api.user.model.CpmsProject;
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
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsCompany requestCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_company_id",
            referencedColumnName = "company_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsCompany userCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "request_project_id",
            referencedColumnName = "project_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsProject requestProject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "response_user_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsUser responseUser;

    /* 요청 유형 코드 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "request_cd",
            referencedColumnName = "code_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ComCode requestCd;

    /* 처리 상태 코드 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "status_cd",
            referencedColumnName = "code_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ComCode statusCd;

    @Column(name = "request_date")
    private LocalDate requestDate;

    @Column(name = "response_date")
    private LocalDate responseDate;

    @Column(name = "support_title")
    private String supportTitle;

    @Column(name = "support_editor")
    private String supportEditor;

    /* 등록자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "reg_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsUser regUser;

    @Column(name = "udt_id", columnDefinition = "int(10) unsigned")
    protected Integer udtId;

    @Column(name = "del_id", columnDefinition = "int(10) unsigned")
    protected Integer delId;

    /* 첨부파일 */
    @OneToMany(mappedBy = "supportRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupportFile> files = new ArrayList<>();

    public SupportRequest(
            CpmsCompany requestCompany,
            CpmsCompany userCompany,
            CpmsProject requestProject,
            CpmsUser responseUser,
            ComCode requestCd,
            ComCode statusCd,
            String requestDate,
            String supportTitle,
            String supportEditor,
            CpmsUser regUser) {
        this.requestCompany = requestCompany;
        this.userCompany = userCompany;
        this.requestProject = requestProject;
        this.responseUser = responseUser;
        this.requestCd = requestCd;
        this.statusCd = statusCd;
        this.requestDate = LocalDate.parse(requestDate);
        this.supportTitle = supportTitle;
        this.supportEditor = supportEditor;
        this.regUser = regUser;
    }

    // 처리 상태 업데이트
    public void updateStatusCd(ComCode statusCd, Integer udtId) {
        this.statusCd = statusCd;
        this.udtId = udtId;
    }

    // 처리 담당자 업데이트
    public void updateResponseUser(CpmsUser responseUser, Integer udtId) {
        this.responseUser = responseUser;
        this.udtId = udtId;
    }

    public void addFile(SupportFile file) {
        if (file != null) {
            files.add(file);
            file.setSupportRequest(this);
        }
    }

    @PrePersist
    protected void onCreate() {
        this.regDt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.udtDt = LocalDateTime.now();
    }
}
