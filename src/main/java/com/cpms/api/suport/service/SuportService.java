package com.cpms.api.suport.service;

import org.springframework.http.ResponseEntity;

import com.cpms.api.suport.dto.req.ReqSuportDTO;

public interface SuportService {

    ResponseEntity<?> insertReqSuport(ReqSuportDTO reqSuportDTO) throws Exception;
}
