package com.cpms.api.suport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cpms.api.suport.dto.req.ReqSuportListDTO;
import com.cpms.api.suport.dto.res.ResSuportListDTO;

public interface CustomSuportReqRepository {

    Page<ResSuportListDTO.SuportList> findSuportList(
            ReqSuportListDTO reqSuportListDTO, Pageable pageable);
}
