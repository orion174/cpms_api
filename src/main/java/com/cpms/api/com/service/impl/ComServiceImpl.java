package com.cpms.api.com.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpms.api.com.dto.req.*;
import com.cpms.api.com.repository.CustomComCodeRepository;
import com.cpms.api.com.service.ComSerivce;
import com.cpms.common.res.ApiRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComServiceImpl implements ComSerivce {

    private final CustomComCodeRepository customComCodeRepository;

    @Override
    public ResponseEntity<?> selectComCodeList(ReqComCodeDTO reqComCodeDTO) {
        return new ResponseEntity<>(
                new ApiRes(customComCodeRepository.selectComCodeList(reqComCodeDTO)),
                HttpStatus.OK);
    }
}
