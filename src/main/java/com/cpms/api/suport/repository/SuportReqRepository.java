package com.cpms.api.suport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.suport.model.SuportReq;

@Repository
public interface SuportReqRepository extends JpaRepository<SuportReq, Integer> {}
