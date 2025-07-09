package com.cpms.api.user.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpms.api.user.dto.request.ReqUserListDTO;
import com.cpms.api.user.dto.response.ResUserListDTO;
import com.cpms.api.user.repository.CpmsUserRepository;
import com.cpms.api.user.service.CpmsUserService;
import com.cpms.common.exception.CustomException;
import com.cpms.common.helper.EntityFinder;
import com.cpms.common.response.ErrorCode;
import com.cpms.common.util.JwtUserUtil;
import com.cpms.common.util.PageUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CpmsUserServiceImpl implements CpmsUserService {

    private final JwtUserUtil jwtUserUtil;

    private final EntityFinder entityFinder;

    private final CpmsUserRepository cpmsUserRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ResUserListDTO> selectCpmsUserList(ReqUserListDTO reqDto) {
        if (!jwtUserUtil.isAdmin()) {
            throw new CustomException(ErrorCode.NO_AUTHORITY);
        }

        Pageable pageable = PageUtil.createPageable(reqDto.getPageNo(), reqDto.getPageSize());

        Page<ResUserListDTO> result = cpmsUserRepository.findCpmsUserList(reqDto, pageable);

        return result;
    }
}
