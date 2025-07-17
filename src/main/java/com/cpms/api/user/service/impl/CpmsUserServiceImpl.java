package com.cpms.api.user.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpms.api.user.dto.request.ReqUserDTO;
import com.cpms.api.user.dto.request.ReqUserListDTO;
import com.cpms.api.user.dto.response.ResUserListDTO;
import com.cpms.api.user.model.CpmsUser;
import com.cpms.api.user.repository.CpmsUserRepository;
import com.cpms.api.user.service.CpmsUserService;
import com.cpms.common.helper.Constants;
import com.cpms.common.helper.EntityFinder;
import com.cpms.common.util.JwtUserUtil;
import com.cpms.common.util.PageUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CpmsUserServiceImpl implements CpmsUserService {

    private final JwtUserUtil jwtUserUtil;

    private final EntityFinder entityFinder;

    private final CpmsUserRepository cpmsUserRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public Page<ResUserListDTO> selectCpmsUserList(ReqUserListDTO reqDTO) {

        Pageable pageable = PageUtil.createPageable(reqDTO.getPageNo(), reqDTO.getPageSize());

        Page<ResUserListDTO> result = cpmsUserRepository.findCpmsUserList(reqDTO, pageable);

        return result;
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void createUser(ReqUserDTO reqDTO) {

        CpmsUser user =
                CpmsUser.fromCreate(
                        reqDTO,
                        passwordEncoder.encode(Constants.INIT_PASSWORD),
                        jwtUserUtil.getUserId());

        cpmsUserRepository.save(user);
    }
}
