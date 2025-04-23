package com.cpms.api.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.code.model.CommonCode;

@Repository
public interface CommonCodeRepository
        extends JpaRepository<CommonCode, Integer>, CustomCommonCodeRepository {}
