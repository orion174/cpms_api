package com.cpms.api.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cpms.api.user.dto.request.ReqUserListDTO;
import com.cpms.api.user.dto.response.ResUserListDTO;

public interface CustomCpmsUserRepository {

    Page<ResUserListDTO> findCpmsUserList(ReqUserListDTO reqUserListDTO, Pageable pageable);
}
