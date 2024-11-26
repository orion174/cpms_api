package com.cpms.api.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.code.model.ComCode;

@Repository
public interface ComCodeRepository
        extends JpaRepository<ComCode, Integer>, CustomComCodeRepository {}
