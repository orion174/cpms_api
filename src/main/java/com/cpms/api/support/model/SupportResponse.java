package com.cpms.api.support.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

import com.cpms.api.auth.model.CpmsUser;
import com.cpms.common.helper.BaseEntity;
import com.cpms.common.helper.YesNo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "support_response")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SupportResponse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "support_response_id")
    private Integer supportResponseId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "support_request_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private SupportRequest supportRequest;

    @Column(name = "response_title")
    @Comment("문의응답 제목")
    private String responseTitle;

    @Column(name = "response_editor")
    @Comment("문의응답 내용")
    private String responseEditor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reg_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CpmsUser regUser;

    @Column(name = "udt_id", columnDefinition = "int(10) unsigned")
    protected Integer udtId;

    @Column(name = "del_id", columnDefinition = "int(10) unsigned")
    protected Integer delId;

    public SupportResponse(
            SupportRequest supportRequest,
            String responseTitle,
            String responseEditor,
            CpmsUser regUser) {
        this.supportRequest = supportRequest;
        this.responseTitle = responseTitle;
        this.responseEditor = responseEditor;
        this.regUser = regUser;
    }

    // 답변 수정
    public void updateResponse(String responseTitle, String responseEditor, Integer udtId) {
        this.responseTitle = responseTitle;
        this.responseEditor = responseEditor;
        this.udtId = udtId;
    }

    // 답변 삭제
    public void deleteResponse(YesNo delYn, Integer delId) {
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
