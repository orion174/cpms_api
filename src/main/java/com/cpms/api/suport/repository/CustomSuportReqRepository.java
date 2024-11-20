package com.cpms.api.suport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cpms.api.suport.dto.req.*;
import com.cpms.api.suport.dto.res.*;

public interface CustomSuportReqRepository {

    Page<ResSuportListDTO.SuportList> findSuportList(
            ReqSuportListDTO reqSuportListDTO, Pageable pageable);

    ResSuportDetailDTO findSuportDetail(Integer suportReqId);

    int updateStatus(ReqSuportDTO reqSuportDTO);

    int updateUser(ReqSuportDTO reqSuportDTO);
}
