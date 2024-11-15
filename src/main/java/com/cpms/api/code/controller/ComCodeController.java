package com.cpms.api.code.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpms.api.code.dto.req.ReqComCodeDTO;
import com.cpms.api.code.service.ComSerivce;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/com/code")
public class ComCodeController {

    private final ComSerivce comSerivce;

    @PostMapping("/list")
    public ResponseEntity<?> selectComCodeList(@RequestBody ReqComCodeDTO reqComCodeDTO) {
        return comSerivce.selectComCodeList(reqComCodeDTO);
    }
}
