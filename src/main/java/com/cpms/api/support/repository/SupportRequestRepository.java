package com.cpms.api.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.support.model.SupportRequest;

@Repository
public interface SupportRequestRepository
        extends JpaRepository<SupportRequest, Integer>, CustomSupportRequestRepository {}
