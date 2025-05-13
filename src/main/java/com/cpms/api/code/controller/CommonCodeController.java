package com.cpms.api.code.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.api.code.dto.request.ReqCommonCodeDTO;
import com.cpms.api.code.service.CommonCodeSerivce;
import com.cpms.common.response.ApiResponse;
import com.cpms.common.response.ResponseMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/code")
public class CommonCodeController {

    private final CommonCodeSerivce commonCodeSerivce;

    @PostMapping("/list")
    public ResponseEntity<ApiResponse> selectCommonCodeList(
            @RequestBody ReqCommonCodeDTO reqCommonCodeDTO) {
        Object result = commonCodeSerivce.selectCommonCodeList(reqCommonCodeDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        result,
                        ResponseMessage.SELECT_SUCCESS.getMessage()));
    }
}
