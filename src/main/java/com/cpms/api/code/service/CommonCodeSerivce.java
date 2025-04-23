package com.cpms.api.code.service;

import java.util.List;

import com.cpms.api.code.dto.request.ReqCommonCodeDTO;
import com.cpms.api.code.dto.response.ResCommonCodeDTO;

public interface CommonCodeSerivce {

    List<ResCommonCodeDTO> selectCommonCodeList(ReqCommonCodeDTO reqCommonCodeDTO);
}
