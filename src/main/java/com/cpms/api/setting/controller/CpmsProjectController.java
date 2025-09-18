package com.cpms.api.setting.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cpms.api.setting.dto.request.ReqProjectDTO;
import com.cpms.api.setting.dto.request.ReqProjectListDTO;
import com.cpms.api.setting.service.CpmsProjectService;
import com.cpms.cmmn.response.ApiResponse;
import com.cpms.cmmn.response.ResponseMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/setting/project")
public class CpmsProjectController {

    private final CpmsProjectService cpmsProjectService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse> selectCpmsProjectList(ReqProjectDTO reqDTO) {
        Object result = cpmsProjectService.selectCpmsProjectList(reqDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        result,
                        ResponseMessage.SELECT_SUCCESS.getMessage()));
    }

    @GetMapping("/admin-list")
    public ResponseEntity<ApiResponse> findCpmsProjectList(ReqProjectListDTO reqDTO) {
        Object result = cpmsProjectService.findCpmsProjectList(reqDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        result,
                        ResponseMessage.SELECT_SUCCESS.getMessage()));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProject(@RequestBody ReqProjectDTO reqDTO) {
        cpmsProjectService.createProject(reqDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.INSERT_SUCCESS.getMessage()));
    }
}
