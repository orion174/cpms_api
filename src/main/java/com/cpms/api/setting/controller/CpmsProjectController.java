package com.cpms.api.setting.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.api.setting.dto.request.ReqProjectDTO;
import com.cpms.api.setting.service.CpmsProjectService;
import com.cpms.common.response.ApiResponse;
import com.cpms.common.response.ResponseMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/setting/project")
public class CpmsProjectController {

    private final CpmsProjectService cpmsProjectService;

    @PostMapping("/list")
    public ResponseEntity<ApiResponse> selectCpmsProjectList(
            @RequestBody ReqProjectDTO reqProjectDTO) {
        Object result = cpmsProjectService.selectCpmsProjectList(reqProjectDTO);

        return ResponseEntity.ok(
                ApiResponse.success(result, ResponseMessage.SELECT_SUCCESS.getMessage()));
    }
}
