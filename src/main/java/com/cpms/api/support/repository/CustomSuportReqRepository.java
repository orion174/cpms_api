package com.cpms.api.support.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cpms.api.support.dto.req.*;
import com.cpms.api.support.dto.res.*;

public interface CustomSuportReqRepository {

    Page<ResSupportListDTO.SuportList> findSuportList(
            ReqSupportListDTO reqSuportListDTO, Pageable pageable);

    ResSupportDetailDTO findSuportDetail(Integer suportReqId);
}
