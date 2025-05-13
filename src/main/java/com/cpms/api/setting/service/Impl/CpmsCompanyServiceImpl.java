package com.cpms.api.setting.service.Impl;

import java.util.List;
import java.util.Optional;

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
        // USER 권한은 특정업체만 선택가능
        // TODO : 고도화 시, 업체 권한 부여 및 기능 분리
        Optional.ofNullable(getAuthType())
                .filter(authType -> "USER".equals(authType))
                .ifPresent(authType -> reqCompanyDTO.setCompanyId(1));

        return cpmsCompanyRepository.selectCpmsCompanyList(reqCompanyDTO);
    }
}
