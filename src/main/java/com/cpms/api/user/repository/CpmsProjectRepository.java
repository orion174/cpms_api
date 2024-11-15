package com.cpms.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.user.model.CpmsProject;

@Repository
public interface CpmsProjectRepository extends JpaRepository<CpmsProject, Integer> {}
