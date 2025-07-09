package com.cpms.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.user.model.CpmsUser;

@Repository
public interface CpmsUserRepository
        extends JpaRepository<CpmsUser, Integer>, CustomCpmsUserRepository {

    boolean existsByLoginId(String loginId);

    boolean existsByUserPhone(String phone);
}
