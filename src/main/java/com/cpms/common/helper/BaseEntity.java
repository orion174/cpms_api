package com.cpms.common.helper;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "reg_dt")
    protected LocalDateTime regDt;

    @Column(name = "udt_dt")
    protected LocalDateTime udtDt;

    @Enumerated(EnumType.STRING)
    @Column(name = "del_yn", columnDefinition = "enum('Y','N')", nullable = false)
    protected YesNo delYn = YesNo.N;

    @Column(name = "del_dt")
    protected LocalDateTime delDt;
}
