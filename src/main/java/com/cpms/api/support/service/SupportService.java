package com.cpms.api.support.service;

import com.cpms.api.support.dto.req.*;
import com.cpms.api.support.dto.res.*;
import org.springframework.http.ResponseEntity;

public interface SupportService {

    boolean insertSupportRequest(ReqSupportDTO reqSuportDTO) throws Exception;

    ResSupportListDTO selectSupportList(ReqSupportListDTO reqSupportListDTO);

    ResSupportDetailDTO selectSupportDetail(ReqSupportDTO reqSupportDTO);

    ResponseEntity<?> fileDownload(int suportFileId);

    ResponseEntity<?> fileDelete(int suportFileId);

    ResponseEntity<?> updateStatus(ReqSupportDTO reqSuportDTO);

    ResponseEntity<?> updateUser(ReqSupportDTO reqSuportDTO);

    ResponseEntity<?> insertResSuport(ReqSupportResponseDTO reqSuportResDTO) throws Exception;

    ResponseEntity<?> updateResSuport(ReqSupportResponseDTO reqSuportResDTO) throws Exception;

    ResponseEntity<?> deleteResSuport(ReqSupportDTO reqSuportDTO) throws Exception;
}
