package com.cpms.api.suport.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.cpms.api.auth.model.CpmsUser;
import com.cpms.common.helper.BaseEntity;
import com.cpms.common.helper.YesNo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "suport_res")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SuportRes extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suport_res_id")
    private Integer suportResId;

    /* 요청 문의 */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suport_req_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private SuportReq suportReq;

    private String resTitle;

    private String resEditor;

    /* 등록자 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsUser regUser;

    @Column(columnDefinition = "int(10) unsigned")
    protected Integer udtId;

    @Column(columnDefinition = "int(10) unsigned")
    protected Integer delId;

    public SuportRes(SuportReq suportReq, String resTitle, String resEditor, CpmsUser regUser) {
        this.suportReq = suportReq;
        this.resTitle = resTitle;
        this.resEditor = resEditor;
        this.regUser = regUser;
    }

    // 답변 수정
    public void updateRes(String resTitle, String resEditor, Integer udtId) {
        this.resTitle = resTitle;
        this.resEditor = resEditor;
        this.udtId = udtId;
    }

    // 답변 삭제
    public void deleteRes(YesNo delYn, Integer delId) {
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
