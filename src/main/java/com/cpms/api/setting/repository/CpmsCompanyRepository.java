package com.cpms.api.setting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.setting.model.CpmsCompany;

@Repository
public interface CpmsCompanyRepository
        extends JpaRepository<CpmsCompany, Integer>, CustomCpmsCompanyRepository {}
