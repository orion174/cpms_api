package com.cpms.api.user.service;

import org.springframework.data.domain.Page;

import com.cpms.api.user.dto.request.ReqUserDTO;
import com.cpms.api.user.dto.request.ReqUserListDTO;
import com.cpms.api.user.dto.response.ResUserListDTO;

public interface CpmsUserService {

    Page<ResUserListDTO> selectCpmsUserList(ReqUserListDTO reqDto);

    void createUser(ReqUserDTO reqDTO);
}
