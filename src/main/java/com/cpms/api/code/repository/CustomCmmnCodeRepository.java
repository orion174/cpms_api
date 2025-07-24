package com.cpms.api.code.repository;

import java.util.List;

import com.cpms.api.code.dto.request.*;
import com.cpms.api.code.dto.response.ResCmmnCodeDTO;

public interface CustomCmmnCodeRepository {

    List<ResCmmnCodeDTO> selectCmmnCodeList(ReqCmmnCodeDTO reqCmmnCodeDTO);
}
