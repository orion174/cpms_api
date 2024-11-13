package com.cpms.api.suport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.suport.model.SuportFile;

@Repository
public interface SuportFileRepository extends JpaRepository<SuportFile, Integer> {}
