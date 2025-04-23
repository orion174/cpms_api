package com.cpms.api.setting.repository;

import java.util.List;

import com.cpms.api.setting.dto.request.ReqProjectDTO;
import com.cpms.api.setting.dto.response.ResProjectListDTO;

public interface CustomCpmsProjectRepository {

    List<ResProjectListDTO> selectCpmsProjectList(ReqProjectDTO reqProjectDTO);
}
