package com.cpms.api.setting.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cpms.api.setting.dto.request.ReqProjectDTO;
import com.cpms.api.setting.dto.request.ReqProjectListDTO;
import com.cpms.api.setting.dto.response.ResProjectListDTO;

public interface CustomCpmsProjectRepository {

    List<ResProjectListDTO> selectCpmsProjectList(ReqProjectDTO reqDTO);

    Page<ResProjectListDTO> findAdminProjectlist(ReqProjectListDTO reqDTO, Pageable pageable);
}
