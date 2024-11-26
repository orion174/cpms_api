package com.cpms.api.suport.model;

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
@Table(name = "suport_req")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuportReq extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suport_req_id")
    private Integer suportReqId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "req_company_id",
            referencedColumnName = "company_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsCompany reqCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_company_id",
            referencedColumnName = "company_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsCompany userCompany;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "req_project_id",
            referencedColumnName = "project_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsProject reqProject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "res_user_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsUser resUser;

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

    private LocalDate reqDate;

    private LocalDate resDate;

    private String suportTitle;

    private String suportEditor;

    /* 등록자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "reg_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsUser regUser;

    @Column(columnDefinition = "int(10) unsigned")
    protected Integer udtId;

    @Column(columnDefinition = "int(10) unsigned")
    protected Integer delId;

    /* 첨부파일 */
    @OneToMany(mappedBy = "suportReq", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SuportFile> files = new ArrayList<>();

    public SuportReq(
            CpmsCompany reqCompany,
            CpmsCompany userCompany,
            CpmsProject reqProject,
            CpmsUser resUser,
            ComCode requestCd,
            ComCode statusCd,
            String reqDate,
            String suportTitle,
            String suportEditor,
            CpmsUser regUser) {
        this.reqCompany = reqCompany;
        this.userCompany = userCompany;
        this.reqProject = reqProject;
        this.resUser = resUser;
        this.requestCd = requestCd;
        this.statusCd = statusCd;
        this.reqDate = LocalDate.parse(reqDate);
        this.suportTitle = suportTitle;
        this.suportEditor = suportEditor;
        this.regUser = regUser;
    }

    // 처리 상태 업데이트
    public void updateStatusCd(ComCode statusCd, Integer udtId) {
        this.statusCd = statusCd;
        this.udtId = udtId;
    }

    // 처리 담당자 업데이트
    public void updateResUser(CpmsUser resUser, Integer udtId) {
        this.resUser = resUser;
        this.udtId = udtId;
    }

    public void addFile(SuportFile file) {
        if (file != null) {
            files.add(file);
            file.setSuportReq(this);
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
