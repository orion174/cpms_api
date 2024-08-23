package com.cpms.api.test.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "sample")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userMasterId;

    @Column private String userId;

    @Column private String userNm;

    @Column private String userAuthCd;

    public TestEntity(Integer userMasterId, String userId, String userNm, String userAuthCd) {
        this.userMasterId = userMasterId;
        this.userId = userId;
        this.userNm = userNm;
        this.userAuthCd = userAuthCd;
    }
}
