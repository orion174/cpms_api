package com.cpms.api.code.repository.impl;

import java.util.List;

import com.cpms.api.code.dto.request.ReqCmmnCodeDTO;
import com.cpms.api.code.dto.response.QResCmmnCodeDTO;
import com.cpms.api.code.dto.response.ResCmmnCodeDTO;
import com.cpms.api.code.model.QCommonCode;
import com.cpms.api.code.repository.CustomCmmnCodeRepository;
import com.cpms.common.helper.YesNo;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CmmnCodeRepositoryImpl implements CustomCmmnCodeRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QCommonCode commonCode = QCommonCode.commonCode;

    @Override
    public List<ResCmmnCodeDTO> selectCmmnCodeList(ReqCmmnCodeDTO reqCmmnCodeDTO) {
        String groupCode = reqCmmnCodeDTO.getGroupCode();

        return jpaQueryFactory
                .select(new QResCmmnCodeDTO(commonCode.codeId, commonCode.codeNm))
                .from(commonCode)
                .where(commonCode.groupCode.eq(groupCode), commonCode.useYn.eq(YesNo.Y))
                .orderBy(commonCode.sortOrder.asc())
                .fetch();
    }
}
