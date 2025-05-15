package com.cpms.api.setting.service.Impl;

import static com.cpms.common.util.CommonUtil.parseToIntSafely;

import java.util.List;

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
        String authType = getAuthType();
        if (!"ADMIN".equals(authType)) {
            reqProjectDTO.setCompanyId(getCompanyId());
        }

        return cpmsProjectRepository.selectCpmsProjectList(reqProjectDTO);
    }
}
