package com.cpms.api.support.service;

import org.springframework.http.ResponseEntity;

import com.cpms.api.support.dto.req.ReqSupportDTO;
import com.cpms.api.support.dto.req.ReqSupportListDTO;
import com.cpms.api.support.dto.req.ReqSupportResponseDTO;

public interface SuportService {

    ResponseEntity<?> insertReqSuport(ReqSupportDTO reqSuportDTO) throws Exception;

    ResponseEntity<?> selectSuportList(ReqSupportListDTO reqSuportListDTO);

    ResponseEntity<?> selectSuportDetail(ReqSupportDTO reqSuportDTO);

    ResponseEntity<?> fileDownload(int suportFileId);

    ResponseEntity<?> fileDelete(int suportFileId);

    ResponseEntity<?> updateStatus(ReqSupportDTO reqSuportDTO);

    ResponseEntity<?> updateUser(ReqSupportDTO reqSuportDTO);

    ResponseEntity<?> insertResSuport(ReqSupportResponseDTO reqSuportResDTO) throws Exception;

    ResponseEntity<?> updateResSuport(ReqSupportResponseDTO reqSuportResDTO) throws Exception;

    ResponseEntity<?> deleteResSuport(ReqSupportDTO reqSuportDTO) throws Exception;
}
