package com.cpms.api.support.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cpms.api.support.dto.req.*;
import com.cpms.api.support.dto.res.*;

public interface CustomSupportRequestRepository {

    Page<ResSupportListDTO.SupportList> findSupportList(
            ReqSupportListDTO reqSupportListDTO, Pageable pageable);

    ResSupportDetailDTO findSupportDetail(Integer supportRequestId);
}
