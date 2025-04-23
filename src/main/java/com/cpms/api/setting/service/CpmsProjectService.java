package com.cpms.api.setting.service;

import java.util.List;

import com.cpms.api.setting.dto.request.ReqProjectDTO;
import com.cpms.api.setting.dto.response.ResProjectListDTO;

public interface CpmsProjectService {

    List<ResProjectListDTO> selectCpmsProjectList(ReqProjectDTO reqProjectDTO);
}
