package com.cpms.api.test.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cpms.api.test.model.TestEntity;

public interface TestService {

    ResponseEntity<?> saveTestInfo(TestEntity testEntity);

    List<TestEntity> seletTestList();
}
