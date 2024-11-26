package com.cpms.api.code.repository;

import java.util.List;

import com.cpms.api.code.dto.req.*;
import com.cpms.api.code.dto.res.*;

public interface CustomComCodeRepository {

    List<ResComCodeDTO> selectComCodeList(ReqComCodeDTO reqComCodeDTO);
}
