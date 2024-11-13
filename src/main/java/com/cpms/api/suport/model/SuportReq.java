package com.cpms.api.suport.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.cpms.common.util.YesNo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "suport_req")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuportReq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer suportReqId;

    @Column(nullable = false)
    private Integer reqCompanyId;

    @Column(nullable = false)
    private Integer userCompanyId;

    @Column(nullable = false)
    private Integer reqProjectId;

    private Integer resUserId;

    private String requestCd;

    private String statusCd;

    private LocalDate reqDate;

    private LocalDate resDate;

    private String suportTitle;

    private String suportEditor;

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

    @OneToMany(mappedBy = "suportReq", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SuportFile> files = new ArrayList<>();

    public SuportReq(
            Integer reqCompanyId,
            Integer userCompanyId,
            Integer reqProjectId,
            Integer resUserId,
            String requestCd,
            String statusCd,
            String reqDate,
            String suportTitle,
            String suportEditor,
            Integer regId) {
        this.reqCompanyId = reqCompanyId;
        this.userCompanyId = userCompanyId;
        this.reqProjectId = reqProjectId;
        this.resUserId = resUserId;
        this.requestCd = requestCd;
        this.statusCd = statusCd;
        this.reqDate = LocalDate.parse(reqDate);
        this.suportTitle = suportTitle;
        this.suportEditor = suportEditor;
        this.regId = regId;
    }

    // 파일 추가 메서드
    public void addFile(SuportFile file) {
        if (file != null) {
            files.add(file);
            file.setSuportReq(this);
        }
    }

    // INSERT
    @PrePersist
    protected void onCreate() {
        this.regDt = LocalDateTime.now();
    }
}
