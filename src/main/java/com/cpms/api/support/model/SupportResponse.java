package com.cpms.api.support.model;

import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

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
    @Comment("문의 글 ID")
    private SupportRequest supportRequest;

    @Column(name = "response_title", length = 255, nullable = false)
    @Comment("응답 제목")
    private String responseTitle;

    @Column(name = "response_editor", columnDefinition = "TEXT")
    @Comment("응답 상세 내용")
    private String responseEditor;

    public SupportResponse(
            SupportRequest supportRequest,
            String responseTitle,
            String responseEditor,
            Integer regId) {
        this.supportRequest = supportRequest;
        this.responseTitle = responseTitle;
        this.responseEditor = responseEditor;
        this.regId = regId;
    }

    public void updateResponse(String responseTitle, String responseEditor, Integer udtId) {
        this.responseTitle = responseTitle;
        this.responseEditor = responseEditor;
        this.udtId = udtId;
    }

    public void deleteResponse(YesNo delYn, Integer delId) {
        this.delYn = delYn;
        this.delId = delId;
    }
}
