package com.cpms.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.user.model.CpmsCompany;

@Repository
public interface CpmsCompanyRepository extends JpaRepository<CpmsCompany, Integer> {}
