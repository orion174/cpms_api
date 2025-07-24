package com.cpms.api.code.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpms.api.code.dto.request.ReqCmmnCodeDTO;
import com.cpms.api.code.dto.response.ResCmmnCodeDTO;
import com.cpms.api.code.repository.CmmnCodeRepository;
import com.cpms.api.code.service.CmmnCodeSerivce;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CmmnServiceImpl implements CmmnCodeSerivce {

    private final CmmnCodeRepository cmmnCodeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ResCmmnCodeDTO> selectCmmnCodeList(ReqCmmnCodeDTO reqCmmnCodeDTO) {
        return cmmnCodeRepository.selectCmmnCodeList(reqCmmnCodeDTO);
    }
}
