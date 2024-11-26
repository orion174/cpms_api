package com.cpms.api.code.repository.impl;

import java.util.List;

import com.cpms.api.code.dto.req.ReqComCodeDTO;
import com.cpms.api.code.dto.res.QResComCodeDTO;
import com.cpms.api.code.dto.res.ResComCodeDTO;
import com.cpms.api.code.model.QComCode;
import com.cpms.api.code.repository.CustomComCodeRepository;
import com.cpms.common.helper.YesNo;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ComCodeRepositoryImpl implements CustomComCodeRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final QComCode comCode = QComCode.comCode;

    @Override
    public List<ResComCodeDTO> selectComCodeList(ReqComCodeDTO reqComCodeDTO) {
        String groupId = reqComCodeDTO.getGroupId();

        return jpaQueryFactory
                .select(new QResComCodeDTO(comCode.codeId, comCode.codeNm))
                .from(comCode)
                .where(comCode.groupId.eq(groupId), comCode.useYn.eq(YesNo.Y))
                .orderBy(comCode.sortOrder.asc())
                .fetch();
    }
}
