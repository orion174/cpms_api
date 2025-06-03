package com.cpms.api.setting.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cpms.api.setting.dto.request.ReqCompanyDTO;
import com.cpms.api.setting.dto.response.ResCompanyListDTO;
import com.cpms.api.setting.repository.CpmsCompanyRepository;
import com.cpms.api.setting.service.CpmsCompanyService;
import com.cpms.common.helper.AuthType;
import com.cpms.common.util.JwtUserUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CpmsCompanyServiceImpl implements CpmsCompanyService {

    private final JwtUserUtil jwtUserUtil;

    private final CpmsCompanyRepository cpmsCompanyRepository;

    @Override
    public List<ResCompanyListDTO> selectCpmsCompanyList(ReqCompanyDTO reqCompanyDTO) {
        if (jwtUserUtil.isNotAdmin()) {
            reqCompanyDTO.setAuthType(AuthType.ADMIN.getCode());
        }

        return cpmsCompanyRepository.selectCpmsCompanyList(reqCompanyDTO);
    }
}
