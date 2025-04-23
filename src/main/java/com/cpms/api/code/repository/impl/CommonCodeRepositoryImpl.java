package com.cpms.api.code.repository.impl;

import java.util.List;

import com.cpms.api.code.dto.request.ReqCommonCodeDTO;
import com.cpms.api.code.dto.response.QResCommonCodeDTO;
import com.cpms.api.code.dto.response.ResCommonCodeDTO;
import com.cpms.api.code.model.QCommonCode;
import com.cpms.api.code.repository.CustomCommonCodeRepository;
import com.cpms.common.helper.YesNo;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommonCodeRepositoryImpl implements CustomCommonCodeRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QCommonCode commonCode = QCommonCode.commonCode;

    @Override
    public List<ResCommonCodeDTO> selectCommonCodeList(ReqCommonCodeDTO reqCommonCodeDTO) {
        String groupCode = reqCommonCodeDTO.getGroupCode();

        return jpaQueryFactory
                .select(new QResCommonCodeDTO(commonCode.codeId, commonCode.codeNm))
                .from(commonCode)
                .where(commonCode.groupCode.eq(groupCode), commonCode.useYn.eq(YesNo.Y))
                .orderBy(commonCode.sortOrder.asc())
                .fetch();
    }
}
