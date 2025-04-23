package com.cpms.api.code.repository;

import java.util.List;

import com.cpms.api.code.dto.request.*;
import com.cpms.api.code.dto.response.ResCommonCodeDTO;

public interface CustomCommonCodeRepository {

    List<ResCommonCodeDTO> selectCommonCodeList(ReqCommonCodeDTO reqCommonCodeDTO);
}
