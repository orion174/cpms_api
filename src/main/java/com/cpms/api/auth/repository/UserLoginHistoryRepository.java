package com.cpms.api.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cpms.api.auth.model.UserLoginHistory;

public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistory, Long> {
    Optional<UserLoginHistory> findByUserId(Long userId);
}
