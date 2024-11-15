package com.cpms.api.code.repository;

import java.util.List;
import java.util.Optional;

import com.cpms.api.code.dto.req.ReqComCodeDTO;
import com.cpms.api.code.dto.res.ResComCodeDTO;
import com.cpms.api.code.model.ComCodeDetail;

public interface CustomComCodeRepository {

    List<ResComCodeDTO> selectComCodeList(ReqComCodeDTO reqComCodeDTO);

    Optional<ComCodeDetail> findByMasterCodeIdAndCodeId(String masterCodeId, String codeId);
}
