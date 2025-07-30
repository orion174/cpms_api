package com.cpms.api.setting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cpms.api.setting.dto.request.ReqCompanyDTO;
import com.cpms.api.setting.dto.request.ReqCompanyListDTO;
import com.cpms.api.setting.service.CpmsCompanyService;
import com.cpms.cmmn.response.ApiResponse;
import com.cpms.cmmn.response.ResponseMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/setting/company")
public class CpmsCompanyController {

    private final CpmsCompanyService cpmsCompanyService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> selectCpmsCompanyList(ReqCompanyDTO reqDTO) {
        Object result = cpmsCompanyService.selectCpmsCompanyList(reqDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        result,
                        ResponseMessage.SELECT_SUCCESS.getMessage()));
    }

    @GetMapping("/admin-list")
    public ResponseEntity<ApiResponse> findAdminCompanylist(ReqCompanyListDTO reqDTO) {
        Object result = cpmsCompanyService.findAdminCompanylist(reqDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        result,
                        ResponseMessage.SELECT_SUCCESS.getMessage()));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCompany(@RequestBody ReqCompanyDTO reqDTO) {
        cpmsCompanyService.createCompany(reqDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.INSERT_SUCCESS.getMessage()));
    }
}
