package com.cpms.api.suport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.suport.model.SuportRes;

@Repository
public interface SuportResRepository extends JpaRepository<SuportRes, Integer> {}
