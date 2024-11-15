package com.cpms.api.suport.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import com.cpms.api.auth.model.CpmsUser;
import com.cpms.common.helper.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "suport_file")
public class SuportFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer suportFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suport_req_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private SuportReq suportReq;

    private String filePath;

    private String fileNm;

    private String fileOgNm;

    private String fileExt;

    private Long fileSize;

    /* 등록자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsUser regUser;

    @Column(columnDefinition = "int(10) unsigned")
    protected Integer udtId;

    @Column(columnDefinition = "int(10) unsigned")
    protected Integer delId;

    public SuportFile(
            SuportReq suportReq,
            String filePath,
            String fileNm,
            String fileOgNm,
            String fileExt,
            Long fileSize,
            CpmsUser regUser) {
        this.suportReq = suportReq;
        this.filePath = filePath;
        this.fileNm = fileNm;
        this.fileOgNm = fileOgNm;
        this.fileExt = fileExt;
        this.fileSize = fileSize;
        this.regUser = regUser;
    }

    public void setSuportReq(SuportReq suportReq) {
        this.suportReq = suportReq;
    }

    @PrePersist
    protected void onCreate() {
        this.regDt = LocalDateTime.now();
    }
}
