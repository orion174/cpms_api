package com.cpms.api.test.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cpms.api.test.model.TestEntity;
import com.cpms.api.test.repository.TestRepository;
import com.cpms.api.test.service.TestService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    /* DB 저장 테스트 */
    @Override
    public void saveTestInfo(TestEntity testEntity) {
        testRepository.save(testEntity);
    }

    /* DB 조회 테스트 */
    @Override
    public List<TestEntity> seletTestList() {
        return testRepository.findAll();
    }
}
