package com.cpms.api.test.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.cpms.api.test.model.TestEntity;
import com.cpms.api.test.service.TestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/save")
    public void saveTestInfo(@RequestBody TestEntity testEntity) {
        testService.saveTestInfo(testEntity);
    }

    @GetMapping("/find")
    public List<TestEntity> seletTestList() {
        return testService.seletTestList();
    }
}
