package com.cpms.api.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.auth.model.CpmsUser;

@Repository
public interface CpmsUserRepository extends JpaRepository<CpmsUser, Integer> {

    boolean existsByLoginId(String loginId);

    boolean existsByUserPhone(String phone);
}
