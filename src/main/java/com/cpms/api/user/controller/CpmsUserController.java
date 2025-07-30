package com.cpms.api.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cpms.api.user.dto.request.ReqUserDTO;
import com.cpms.api.user.dto.request.ReqUserListDTO;
import com.cpms.api.user.service.CpmsUserService;
import com.cpms.cmmn.response.ApiResponse;
import com.cpms.cmmn.response.ResponseMessage;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class CpmsUserController {

    private final CpmsUserService cpmsUserService;

    @PostMapping("/list")
    public ResponseEntity<ApiResponse> selectCpmsUserList(@RequestBody ReqUserListDTO reqDTO) {
        Object result = cpmsUserService.selectCpmsUserList(reqDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(),
                        result,
                        ResponseMessage.SELECT_SUCCESS.getMessage()));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody ReqUserDTO reqDTO) {
        cpmsUserService.createUser(reqDTO);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK.value(), ResponseMessage.INSERT_SUCCESS.getMessage()));
    }
}
