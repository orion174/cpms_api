package com.cpms.api.support.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cpms.api.support.dto.request.*;
import com.cpms.api.support.dto.response.*;

public interface CustomSupportRequestRepository {

    Page<ResSupportListDTO.SupportList> findSupportList(
            ReqSupportListDTO reqSupportListDTO, Pageable pageable);

    ResSupportViewDTO findSupportView(Integer supportRequestId);
}
