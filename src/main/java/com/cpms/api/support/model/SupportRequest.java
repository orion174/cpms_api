package com.cpms.api.support.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

import com.cpms.api.auth.model.CpmsUser;
import com.cpms.api.code.model.CommonCode;
import com.cpms.api.setting.model.CpmsCompany;
import com.cpms.api.setting.model.CpmsProject;
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

    /** 해당 회사 ID의 업체에게 문의를 요청 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "request_company_id",
            referencedColumnName = "company_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsCompany requestCompany;

    /** 문의를 등록한 사용자의 회사 ID */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_company_id",
            referencedColumnName = "company_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsCompany userCompany;

    /** 프로젝트 ID */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "request_project_id",
            referencedColumnName = "project_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsProject requestProject;

    /** 해당 문의에 응답한 사용자 ID */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "response_user_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsUser responseUser;

    /** 요청 유형 분류 코드 (공통 코드를 참조) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "request_cd",
            referencedColumnName = "code_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CommonCode requestCd;

    /** 처리 상태 분류 코드 (공통 코드를 참조) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "status_cd",
            referencedColumnName = "code_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CommonCode statusCd;

    @Column(name = "request_date")
    @Comment("요청 일자")
    private LocalDate requestDate;

    @Column(name = "response_date")
    @Comment("응답 일자")
    private LocalDate responseDate;

    @Column(name = "support_title")
    @Comment("문의 제목")
    private String supportTitle;

    @Column(name = "support_editor")
    @Comment("문의 상세 내용")
    private String supportEditor;

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

    /** 문의 요청 및 응답 첨부 파일 */
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

    /**
     * 처리상태를 업데이트
     *
     * @param statusCd
     * @param udtId
     */
    public void updateStatusCd(CommonCode statusCd, Integer udtId) {
        this.statusCd = statusCd;
        this.udtId = udtId;
    }

    /**
     * 처리 담당자를 업데이트
     *
     * @param responseUser
     * @param udtId
     */
    public void updateResponseUser(CpmsUser responseUser, Integer udtId) {
        this.responseUser = responseUser;
        this.udtId = udtId;
    }

    /**
     * 문의 요청 및 응답에 대한 파일을 추가한다.
     *
     * @param file
     */
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
