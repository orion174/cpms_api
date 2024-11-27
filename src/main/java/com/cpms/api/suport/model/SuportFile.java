package com.cpms.api.suport.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import com.cpms.api.auth.model.CpmsUser;
import com.cpms.common.helper.BaseEntity;
import com.cpms.common.helper.YesNo;

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
    @Column(name = "suport_file_id")
    private Integer suportFileId;

    @Column(name = "file_type")
    private String fileType;

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
            String fileType,
            String filePath,
            String fileNm,
            String fileOgNm,
            String fileExt,
            Long fileSize,
            CpmsUser regUser) {
        this.suportReq = suportReq;
        this.fileType = fileType;
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

    // 파일 삭제
    public void deleteFile(YesNo delYn, Integer delId) {
        this.delYn = delYn;
        this.delId = delId;
    }

    @PrePersist
    protected void onCreate() {
        this.regDt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        if (YesNo.Y.equals(this.delYn)) {
            this.delDt = LocalDateTime.now();

        } else {
            this.udtDt = LocalDateTime.now();
        }
    }
}
