package com.cpms.api.setting.repository;

import java.util.List;

import com.cpms.api.setting.dto.request.ReqCompanyDTO;
import com.cpms.api.setting.dto.response.ResCompanyListDTO;

public interface CustomCpmsCompanyRepository {

    List<ResCompanyListDTO> selectCpmsCompanyList(ReqCompanyDTO reqCompanyDTO);
}
