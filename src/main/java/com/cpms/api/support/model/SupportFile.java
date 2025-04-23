package com.cpms.api.support.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import org.hibernate.annotations.Comment;

import com.cpms.api.auth.model.CpmsUser;
import com.cpms.common.helper.BaseEntity;
import com.cpms.common.helper.YesNo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "support_file")
public class SupportFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "support_file_id")
    private Integer supportFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "support_request_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private SupportRequest supportRequest;

    @Column(name = "file_type")
    @Comment("파일 구분")
    private String fileType;

    @Column(name = "file_path")
    @Comment("파일 물리 경로")
    private String filePath;

    @Column(name = "file_nm")
    @Comment("변환된 파일 명")
    private String fileNm;

    @Column(name = "file_og_nm")
    @Comment("실제 파일 명")
    private String fileOgNm;

    @Column(name = "file_ext")
    @Comment("파일 확장자")
    private String fileExt;

    @Column(name = "file_size")
    @Comment("파일 크기")
    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsUser regUser;

    @Column(name = "udt_id", columnDefinition = "int(10) unsigned")
    protected Integer udtId;

    @Column(name = "del_id", columnDefinition = "int(10) unsigned")
    protected Integer delId;

    public SupportFile(
            SupportRequest supportRequest,
            String fileType,
            String filePath,
            String fileNm,
            String fileOgNm,
            String fileExt,
            Long fileSize,
            CpmsUser regUser) {
        this.supportRequest = supportRequest;
        this.fileType = fileType;
        this.filePath = filePath;
        this.fileNm = fileNm;
        this.fileOgNm = fileOgNm;
        this.fileExt = fileExt;
        this.fileSize = fileSize;
        this.regUser = regUser;
    }

    public void setSupportRequest(SupportRequest supportRequest) {
        this.supportRequest = supportRequest;
    }

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
