package com.cpms.api.setting.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cpms.api.setting.dto.request.ReqCompanyDTO;
import com.cpms.api.setting.dto.response.ResCompanyListDTO;
import com.cpms.api.setting.repository.CpmsCompanyRepository;
import com.cpms.api.setting.service.CpmsCompanyService;
import com.cpms.common.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CpmsCompanyServiceImpl implements CpmsCompanyService {

    private final CpmsCompanyRepository cpmsCompanyRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private String getAuthType() {
        return jwtTokenProvider.getClaim("authType");
    }

    @Override
    public List<ResCompanyListDTO> selectCpmsCompanyList(ReqCompanyDTO reqCompanyDTO) {
        String authType = getAuthType();
        if (!"ADMIN".equals(authType)) {
            reqCompanyDTO.setAuthType("ADMIN");
        }

        return cpmsCompanyRepository.selectCpmsCompanyList(reqCompanyDTO);
    }
}
