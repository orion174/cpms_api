package com.cpms.api.code.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpms.api.code.dto.req.ReqComCodeDTO;
import com.cpms.api.code.repository.ComCodeRepository;
import com.cpms.api.code.service.ComCodeSerivce;
import com.cpms.common.res.ApiRes;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComServiceImpl implements ComCodeSerivce {

    private final ComCodeRepository comCodeRepository;

    @Override
    public ResponseEntity<?> selectComCodeList(ReqComCodeDTO reqComCodeDTO) {
        return new ResponseEntity<>(
                new ApiRes(comCodeRepository.selectComCodeList(reqComCodeDTO)), HttpStatus.OK);
    }
}
