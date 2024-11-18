package com.cpms.api.suport.service;

import org.springframework.http.ResponseEntity;

import com.cpms.api.suport.dto.req.ReqSuportDTO;
import com.cpms.api.suport.dto.req.ReqSuportListDTO;

public interface SuportService {

    ResponseEntity<?> insertReqSuport(ReqSuportDTO reqSuportDTO) throws Exception;

    ResponseEntity<?> selectSuportList(ReqSuportListDTO reqSuportListDTO);

    ResponseEntity<?> selectSuportDetail(ReqSuportDTO reqSuportDTO);
}
