package com.cpms.api.code.service;

import java.util.List;

import com.cpms.api.code.dto.request.ReqCmmnCodeDTO;
import com.cpms.api.code.dto.response.ResCmmnCodeDTO;

public interface CmmnCodeSerivce {

    List<ResCmmnCodeDTO> selectCmmnCodeList(ReqCmmnCodeDTO reqCmmnCodeDTO);
}
