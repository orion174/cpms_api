package com.cpms.api.suport.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.cpms.common.helper.BaseEntity;

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
    private Integer suportResId;

    /* 요청 문의글 */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suport_req_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private SuportReq suportReq;

    private String resEditor;

    public SuportRes(SuportReq suportReq, String resEditor) {
        this.suportReq = suportReq;
        this.resEditor = resEditor;
    }

    @PrePersist
    protected void onCreate() {
        this.regDt = LocalDateTime.now();
    }
}
