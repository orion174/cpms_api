package com.cpms.api.auth.model;

import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

import com.cpms.cmmn.helper.YesNo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "cpms_auth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CpmsAuth {

    @Id
    @Column(name = "auth_type", length = 10)
    @Comment("권한 분류")
    private String authType;

    @Column(name = "auth_nm", length = 30, nullable = false)
    @Comment("권한 명")
    private String authNm;

    @Column(name = "sort_order", columnDefinition = "int(10) unsigned", nullable = false)
    @Comment("코드 정렬 순서")
    private Integer sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('Y','N')", nullable = false)
    @Comment("사용 유무")
    private YesNo useYn = YesNo.Y;
}
