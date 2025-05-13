package com.cpms.api.setting.service;

import java.util.List;

import com.cpms.api.setting.dto.request.ReqCompanyDTO;
import com.cpms.api.setting.dto.response.ResCompanyListDTO;

public interface CpmsCompanyService {

    List<ResCompanyListDTO> selectCpmsCompanyList(ReqCompanyDTO reqCompanyDTO);
}
