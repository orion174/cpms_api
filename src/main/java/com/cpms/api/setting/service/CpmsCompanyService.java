package com.cpms.api.setting.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cpms.api.setting.dto.request.*;
import com.cpms.api.setting.dto.response.ResCompanyListDTO;

public interface CpmsCompanyService {

    List<ResCompanyListDTO> selectCpmsCompanyList(ReqCompanyDTO reqDTO);

    Page<ResCompanyListDTO> findAdminCompanylist(ReqCompanyListDTO reqDTO);

    void createCompany(ReqCompanyDTO reqDTO);
}
