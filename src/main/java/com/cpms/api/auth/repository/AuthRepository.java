package com.cpms.api.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.cpms.api.user.model.CpmsUser;

@Repository
public interface AuthRepository
        extends JpaRepository<CpmsUser, Long>, QuerydslPredicateExecutor<CpmsUser> {}
