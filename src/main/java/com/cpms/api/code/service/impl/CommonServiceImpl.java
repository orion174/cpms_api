package com.cpms.api.code.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cpms.api.code.dto.request.ReqCommonCodeDTO;
import com.cpms.api.code.dto.response.ResCommonCodeDTO;
import com.cpms.api.code.repository.CommonCodeRepository;
import com.cpms.api.code.service.CommonCodeSerivce;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonCodeSerivce {

    private final CommonCodeRepository commonCodeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ResCommonCodeDTO> selectCommonCodeList(ReqCommonCodeDTO reqCommonCodeDTO) {
        return commonCodeRepository.selectCommonCodeList(reqCommonCodeDTO);
    }
}
