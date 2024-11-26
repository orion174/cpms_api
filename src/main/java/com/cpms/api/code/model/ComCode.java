package com.cpms.api.code.model;

import jakarta.persistence.*;

import com.cpms.common.helper.YesNo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "com_code")
public class ComCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Integer codeId;

    @Column(name = "group_id", length = 10)
    private String groupId;

    @Column(name = "code_nm", length = 30)
    private String codeNm;

    @Column(name = "depth", columnDefinition = "int(10) unsigned", nullable = false)
    private Integer depth;

    @Column(name = "sort_order", columnDefinition = "int(10) unsigned", nullable = false)
    private Integer sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('Y','N')", nullable = false)
    private YesNo useYn = YesNo.Y;
}
