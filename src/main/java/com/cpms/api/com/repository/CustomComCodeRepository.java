package com.cpms.api.com.repository;

import java.util.List;

import com.cpms.api.com.dto.req.*;
import com.cpms.api.com.dto.res.*;

public interface CustomComCodeRepository {

    List<ResComCodeDTO> selectComCodeList(ReqComCodeDTO reqComCodeDTO);
}
