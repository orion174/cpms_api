package com.cpms.api.test.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "sample")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userMasterId;

    @Column private String userId;

    @Column private String userPw;

    @Column private String userNm;

    @Column private String userPhone;

    @Column private String userZoneCode;

    @Column private String userAddress;

    @Column private String userDetailAddress;

    @Column private String userInfo;
}
