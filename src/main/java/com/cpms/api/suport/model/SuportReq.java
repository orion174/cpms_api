package com.cpms.api.suport.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.cpms.api.auth.model.CpmsUser;
import com.cpms.api.code.model.ComCodeDetail;
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
    private Integer suportReqId;

    /* 요청 회사 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "req_company_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsCompany reqCompany;

    /* 문의 회사 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_company_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsCompany userCompany;

    /* 프로젝트 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "req_project_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsProject reqProject;

    /* 처리 담당자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsUser resUser;

    /* 요청 유형 코드 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "request_cd",
            referencedColumnName = "code_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ComCodeDetail requestCdDetail;

    /* 처리 상태 코드 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "status_cd",
            referencedColumnName = "code_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ComCodeDetail statusCdDetail;

    private LocalDate reqDate;

    private LocalDate resDate;

    private String suportTitle;

    private String suportEditor;

    /* 등록자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsUser regUser;

    @Column(columnDefinition = "int(10) unsigned")
    protected Integer udtId;

    @Column(columnDefinition = "int(10) unsigned")
    protected Integer delId;

    /* 첨부파일 */
    @OneToMany(mappedBy = "suportReq", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SuportFile> files = new ArrayList<>();

    /* 유지보수 응답 */
    @OneToOne(mappedBy = "suportReq", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private SuportRes suportRes;

    public SuportReq(
            CpmsCompany reqCompany,
            CpmsCompany userCompany,
            CpmsProject reqProject,
            CpmsUser resUser,
            ComCodeDetail requestCdDetail,
            ComCodeDetail statusCdDetail,
            String reqDate,
            String suportTitle,
            String suportEditor,
            CpmsUser regUser,
            SuportRes suportRes) {
        this.reqCompany = reqCompany;
        this.userCompany = userCompany;
        this.reqProject = reqProject;
        this.resUser = resUser;
        this.requestCdDetail = requestCdDetail;
        this.statusCdDetail = statusCdDetail;
        this.reqDate = LocalDate.parse(reqDate);
        this.suportTitle = suportTitle;
        this.suportEditor = suportEditor;
        this.regUser = regUser;
        this.suportRes = suportRes;
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
}
