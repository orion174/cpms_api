package com.cpms.api.setting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.setting.model.CpmsProject;

@Repository
public interface CpmsProjectRepository
        extends JpaRepository<CpmsProject, Integer>, CustomCpmsProjectRepository {}
