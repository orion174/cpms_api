package com.cpms.api.setting.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cpms.api.setting.dto.request.ReqCompanyDTO;
import com.cpms.api.setting.dto.request.ReqCompanyListDTO;
import com.cpms.api.setting.dto.response.ResCompanyListDTO;

public interface CustomCpmsCompanyRepository {

    List<ResCompanyListDTO> selectCpmsCompanyList(ReqCompanyDTO reqCompanyDTO);

    Page<ResCompanyListDTO> findAdminCompanylist(
            ReqCompanyListDTO reqCompanyListDTO, Pageable pageable);
}
