package com.cpms.api.suport.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import com.cpms.common.util.YesNo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "suport_file")
public class SuportFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer suportFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suport_req_id")
    private SuportReq suportReq;

    private String fileCategory;

    private String filePath;

    private String fileNm;

    private String fileOgNm;

    private String fileExt;

    private Long fileSize;

    @Column(nullable = false)
    private Integer regId;

    @Column(nullable = false, columnDefinition = "datetime default current_timestamp()")
    private LocalDateTime regDt;

    private Integer udtId;

    private LocalDateTime udtDt;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('Y','N')", nullable = false)
    private YesNo delYn = YesNo.N;

    private Integer delId;

    private LocalDateTime delDt;

    public SuportFile(
            String filePath,
            String fileNm,
            String fileOgNm,
            String fileExt,
            Long fileSize,
            Integer regId,
            SuportReq suportReq) {
        this.filePath = filePath;
        this.fileNm = fileNm;
        this.fileOgNm = fileOgNm;
        this.fileExt = fileExt;
        this.fileSize = fileSize;
        this.regId = regId;
        this.regDt = LocalDateTime.now();
        this.suportReq = suportReq;
    }

    public void setSuportReq(SuportReq suportReq) {
        this.suportReq = suportReq;
    }

    @PrePersist
    protected void onCreate() {
        this.regDt = LocalDateTime.now();
    }
}
