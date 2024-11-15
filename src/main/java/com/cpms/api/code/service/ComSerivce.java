package com.cpms.api.code.service;

import org.springframework.http.ResponseEntity;

import com.cpms.api.code.dto.req.ReqComCodeDTO;

public interface ComSerivce {

    ResponseEntity<?> selectComCodeList(ReqComCodeDTO reqComCodeDTO);
}
