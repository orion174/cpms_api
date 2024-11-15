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

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('Y','N')", nullable = false)
    protected YesNo delYn = YesNo.N;

    @Column protected LocalDateTime regDt;

    @Column protected LocalDateTime udtDt;

    @Column protected LocalDateTime delDt;
}
