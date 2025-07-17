package com.cpms.api.setting.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cpms.api.setting.dto.request.ReqProjectDTO;
import com.cpms.api.setting.dto.request.ReqProjectListDTO;
import com.cpms.api.setting.dto.response.ResProjectListDTO;

public interface CpmsProjectService {

    List<ResProjectListDTO> selectCpmsProjectList(ReqProjectDTO reqDTO);

    Page<ResProjectListDTO> findCpmsProjectList(ReqProjectListDTO reqDTO);

    void createProject(ReqProjectDTO reqDTO);
}
