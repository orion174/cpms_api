package com.cpms.api.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.api.user.dto.request.ReqUserListDTO;
import com.cpms.api.user.service.CpmsUserService;
import com.cpms.common.response.ApiResponse;
import com.cpms.common.response.ResponseMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class CpmsUserController {

    private final CpmsUserService cpmsUserService;

    @PostMapping("/list")
    public ResponseEntity<ApiResponse> selectSupportList(@RequestBody ReqUserListDTO reqDto) {
        Object result = cpmsUserService.selectCpmsUserList(reqDto);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        result,
                        ResponseMessage.SELECT_SUCCESS.getMessage()));
    }
}
