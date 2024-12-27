package com.cpms.api.suport.service;

import org.springframework.http.ResponseEntity;

import com.cpms.api.suport.dto.req.ReqSuportDTO;
import com.cpms.api.suport.dto.req.ReqSuportListDTO;
import com.cpms.api.suport.dto.req.ReqSuportResDTO;

public interface SuportService {

    ResponseEntity<?> insertReqSuport(ReqSuportDTO reqSuportDTO) throws Exception;

    ResponseEntity<?> selectSuportList(ReqSuportListDTO reqSuportListDTO);

    ResponseEntity<?> selectSuportDetail(ReqSuportDTO reqSuportDTO);

    ResponseEntity<?> fileDownload(int suportFileId);

    ResponseEntity<?> fileDelete(int suportFileId);

    ResponseEntity<?> updateStatus(ReqSuportDTO reqSuportDTO);

    ResponseEntity<?> updateUser(ReqSuportDTO reqSuportDTO);

    ResponseEntity<?> insertResSuport(ReqSuportResDTO reqSuportResDTO) throws Exception;

    ResponseEntity<?> updateResSuport(ReqSuportResDTO reqSuportResDTO) throws Exception;

    ResponseEntity<?> deleteResSuport(ReqSuportDTO reqSuportDTO) throws Exception;
}
