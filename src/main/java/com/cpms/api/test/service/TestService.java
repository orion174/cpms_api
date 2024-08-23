package com.cpms.api.test.service;

import java.util.List;

import com.cpms.api.test.model.TestEntity;

public interface TestService {

    void saveTestInfo(TestEntity testEntity);

    List<TestEntity> seletTestList();
}
