package com.cpms.api.setting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.api.setting.dto.request.ReqCompanyDTO;
import com.cpms.api.setting.service.CpmsCompanyService;
import com.cpms.common.response.ApiResponse;
import com.cpms.common.response.ResponseMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/setting/company")
public class CpmsCompanyController {

    private final CpmsCompanyService cpmsCompanyService;

    @PostMapping("/list")
    public ResponseEntity<ApiResponse> selectCpmsCompanyList(
            @RequestBody ReqCompanyDTO reqCompanyDTO) {
        Object result = cpmsCompanyService.selectCpmsCompanyList(reqCompanyDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        result,
                        ResponseMessage.SELECT_SUCCESS.getMessage()));
    }
}
