package com.cpms.api.com.service;

import org.springframework.http.ResponseEntity;

import com.cpms.api.com.dto.req.ReqComCodeDTO;

public interface ComSerivce {

    ResponseEntity<?> selectComCodeList(ReqComCodeDTO reqComCodeDTO);
}
