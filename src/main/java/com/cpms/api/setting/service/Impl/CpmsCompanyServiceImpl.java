package com.cpms.api.setting.service.Impl;

import java.util.List;

import jakarta.annotation.security.PermitAll;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpms.api.setting.dto.request.ReqCompanyDTO;
import com.cpms.api.setting.dto.request.ReqCompanyListDTO;
import com.cpms.api.setting.dto.response.ResCompanyListDTO;
import com.cpms.api.setting.model.CpmsCompany;
import com.cpms.api.setting.repository.CpmsCompanyRepository;
import com.cpms.api.setting.service.CpmsCompanyService;
import com.cpms.cmmn.helper.AuthType;
import com.cpms.cmmn.util.JwtUserUtil;
import com.cpms.cmmn.util.PageUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CpmsCompanyServiceImpl implements CpmsCompanyService {

    private final JwtUserUtil jwtUserUtil;

    private final CpmsCompanyRepository cpmsCompanyRepository;

    @Override
    @PermitAll
    @Transactional(readOnly = true)
    public List<ResCompanyListDTO> selectCpmsCompanyList(ReqCompanyDTO reqDTO) {

        if (jwtUserUtil.isNotAdmin()) {
            reqDTO.setAuthType(AuthType.ADMIN.getCode());
        }

        return cpmsCompanyRepository.selectCpmsCompanyList(reqDTO);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ResCompanyListDTO> findAdminCompanylist(ReqCompanyListDTO reqDTO) {

        Pageable pageable = PageUtil.createPageable(reqDTO.getPageNo(), reqDTO.getPageSize());

        Page<ResCompanyListDTO> result =
                cpmsCompanyRepository.findAdminCompanylist(reqDTO, pageable);

        return result;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void createCompany(ReqCompanyDTO reqDTO) {

        CpmsCompany company = CpmsCompany.from(reqDTO, jwtUserUtil.getUserId());

        cpmsCompanyRepository.save(company);
    }
}
