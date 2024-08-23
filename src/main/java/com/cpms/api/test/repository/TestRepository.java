package com.cpms.api.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cpms.api.test.model.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Integer> {}
