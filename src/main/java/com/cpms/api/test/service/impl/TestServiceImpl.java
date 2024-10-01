package com.cpms.api.test.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpms.api.test.model.TestEntity;
import com.cpms.api.test.repository.TestRepository;
import com.cpms.api.test.service.TestService;
import com.cpms.common.res.ApiRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    /* DB 저장 테스트 */
    @Override
    public ResponseEntity<?> saveTestInfo(TestEntity testEntity) {
        return new ResponseEntity<>(new ApiRes(testRepository.save(testEntity)), HttpStatus.OK);
    }

    /* DB 조회 테스트 */
    @Override
    public List<TestEntity> seletTestList() {
        return testRepository.findAll();
    }
}
