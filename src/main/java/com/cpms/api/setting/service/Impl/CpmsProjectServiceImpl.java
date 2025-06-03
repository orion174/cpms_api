package com.cpms.api.setting.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cpms.api.setting.dto.request.ReqProjectDTO;
import com.cpms.api.setting.dto.response.ResProjectListDTO;
import com.cpms.api.setting.repository.CpmsProjectRepository;
import com.cpms.api.setting.service.CpmsProjectService;
import com.cpms.common.util.JwtUserUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CpmsProjectServiceImpl implements CpmsProjectService {

    private final JwtUserUtil jwtUserUtil;

    private final CpmsProjectRepository cpmsProjectRepository;

    @Override
    public List<ResProjectListDTO> selectCpmsProjectList(ReqProjectDTO reqProjectDTO) {
        if (jwtUserUtil.isNotAdmin()) {
            reqProjectDTO.setCompanyId(jwtUserUtil.getCompanyId());
        }

        return cpmsProjectRepository.selectCpmsProjectList(reqProjectDTO);
    }
}
