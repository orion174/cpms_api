package com.cpms.api.support.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import org.hibernate.annotations.Comment;

import com.cpms.common.helper.BaseEntity;
import com.cpms.common.helper.YesNo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "support_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SupportFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "support_file_id")
    private Integer supportFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "support_request_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Comment("문의 글 ID")
    private SupportRequest supportRequest;

    @Column(name = "file_type", length = 30, nullable = false)
    @Comment("파일 구분")
    private String fileType;

    @Column(name = "file_path", length = 255, nullable = false)
    @Comment("파일 물리 경로")
    private String filePath;

    @Column(name = "file_nm", length = 255, nullable = false)
    @Comment("변환된 파일 명")
    private String fileNm;

    @Column(name = "file_og_nm", length = 255, nullable = false)
    @Comment("실제 파일 명")
    private String fileOgNm;

    @Column(name = "file_ext", length = 255, nullable = false)
    @Comment("파일 확장자")
    private String fileExt;

    @Column(name = "file_size", columnDefinition = "BIGINT")
    @Comment("파일 크기")
    private Long fileSize;

    public SupportFile(
            SupportRequest supportRequest,
            String fileType,
            String filePath,
            String fileNm,
            String fileOgNm,
            String fileExt,
            Long fileSize,
            Integer regId) {
        this.supportRequest = supportRequest;
        this.fileType = fileType;
        this.filePath = filePath;
        this.fileNm = fileNm;
        this.fileOgNm = fileOgNm;
        this.fileExt = fileExt;
        this.fileSize = fileSize;
        this.regId = regId;
    }

    public void setSupportRequest(SupportRequest supportRequest) {
        this.supportRequest = supportRequest;
    }

    public void deleteFile(YesNo delYn, Integer delId) {
        this.delYn = delYn;
        this.delId = delId;
    }
}
