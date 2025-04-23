package com.cpms.api.setting.service.Impl;

import static com.cpms.common.util.CommonUtil.parseToIntSafely;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cpms.api.setting.dto.request.ReqProjectDTO;
import com.cpms.api.setting.dto.response.ResProjectListDTO;
import com.cpms.api.setting.repository.CpmsProjectRepository;
import com.cpms.api.setting.service.CpmsProjectService;
import com.cpms.common.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CpmsProjectServiceImpl implements CpmsProjectService {

    private final CpmsProjectRepository cpmsProjectRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private Integer getCompanyId() {
        String companyIdStr = jwtTokenProvider.getClaim("companyId");
        return parseToIntSafely(companyIdStr);
    }

    private String getAuthType() {
        return jwtTokenProvider.getClaim("authType");
    }

    @Override
    public List<ResProjectListDTO> selectCpmsProjectList(ReqProjectDTO reqProjectDTO) {
        // USER 권한은 소속 업체 프로젝트만 조회가능
        Optional.ofNullable(getAuthType())
                .filter(authType -> "USER".equals(authType))
                .ifPresent(authType -> reqProjectDTO.setCompanyId(getCompanyId()));

        return cpmsProjectRepository.selectCpmsProjectList(reqProjectDTO);
    }
}
