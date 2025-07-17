package com.cpms.api.setting.service.Impl;

import java.util.List;

import jakarta.annotation.security.PermitAll;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpms.api.setting.dto.request.ReqProjectDTO;
import com.cpms.api.setting.dto.request.ReqProjectListDTO;
import com.cpms.api.setting.dto.response.ResProjectListDTO;
import com.cpms.api.setting.model.CpmsProject;
import com.cpms.api.setting.repository.CpmsProjectRepository;
import com.cpms.api.setting.service.CpmsProjectService;
import com.cpms.common.util.JwtUserUtil;
import com.cpms.common.util.PageUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CpmsProjectServiceImpl implements CpmsProjectService {

    private final JwtUserUtil jwtUserUtil;

    private final CpmsProjectRepository cpmsProjectRepository;

    @Override
    @PermitAll
    @Transactional(readOnly = true)
    public List<ResProjectListDTO> selectCpmsProjectList(ReqProjectDTO reqDTO) {

        if (jwtUserUtil.isNotAdmin()) {
            reqDTO.setCompanyId(jwtUserUtil.getCompanyId());
        }

        return cpmsProjectRepository.selectCpmsProjectList(reqDTO);
    }

    @Override
    @PermitAll
    @Transactional(readOnly = true)
    public Page<ResProjectListDTO> findCpmsProjectList(ReqProjectListDTO reqDTO) {

        if (jwtUserUtil.isNotAdmin()) {
            reqDTO.setCompanyId(jwtUserUtil.getCompanyId());
        }

        Pageable pageable = PageUtil.createPageable(reqDTO.getPageNo(), reqDTO.getPageSize());

        Page<ResProjectListDTO> result =
                cpmsProjectRepository.findAdminProjectlist(reqDTO, pageable);

        return result;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void createProject(ReqProjectDTO reqDTO) {

        CpmsProject project = CpmsProject.from(reqDTO, jwtUserUtil.getUserId());

        cpmsProjectRepository.save(project);
    }
}
