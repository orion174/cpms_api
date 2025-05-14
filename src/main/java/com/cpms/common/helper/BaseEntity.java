package com.cpms.common.helper;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "reg_id", columnDefinition = "int(10) unsigned", nullable = false)
    @Comment("데이터 최초 등록자 고유 ID")
    protected Integer regId;

    @Column(name = "reg_dt")
    @Comment("데이터 최초 생성일자")
    protected LocalDateTime regDt;

    @Column(name = "udt_id", columnDefinition = "int(10) unsigned")
    @Comment("데이터 마지막 수정자 고유 ID")
    protected Integer udtId;

    @Column(name = "udt_dt")
    @Comment("데이터 마지막 수정일자")
    protected LocalDateTime udtDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "del_yn", columnDefinition = "enum('Y','N')", nullable = false)
    @Comment("데이터 삭제 유무")
    protected YesNo delYn = YesNo.N;

    @Column(name = "del_id", columnDefinition = "int(10) unsigned")
    @Comment("데이터 삭제자 고유 ID")
    protected Integer delId;

    @Column(name = "del_dt")
    @Comment("데이터 삭제일자")
    protected LocalDateTime delDt;

    @PrePersist
    protected void onCreate() {
        this.regDt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.udtDt = LocalDateTime.now();

        if (this.delYn == YesNo.Y) {
            this.delDt = LocalDateTime.now();
        }
    }
}
