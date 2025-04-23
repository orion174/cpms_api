package com.cpms.api.code.model;

import jakarta.persistence.*;

import org.hibernate.annotations.Comment;

import com.cpms.common.helper.YesNo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "common_code")
public class CommonCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Integer codeId;

    @Column(name = "group_code", length = 10, nullable = false)
    @Comment("공통 코드 그룹")
    private String groupCode;

    @Column(name = "code_nm", length = 30)
    @Comment("공통 코드 명")
    private String codeNm;

    @Column(name = "depth", columnDefinition = "int(10) unsigned", nullable = false)
    @Comment("공콩 코드 계층 분류")
    private Integer depth;

    @Column(name = "sort_order", columnDefinition = "int(10) unsigned", nullable = false)
    @Comment("코드 정렬 순서")
    private Integer sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('Y','N')", nullable = false)
    @Comment("사용 유무")
    private YesNo useYn = YesNo.Y;
}
