package com.cpms.api.com.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cpms.api.com.dto.req.ReqComCodeDTO;
import com.cpms.api.com.dto.res.QResComCodeDTO;
import com.cpms.api.com.dto.res.ResComCodeDTO;
import com.cpms.api.com.model.QComCodeDetail;
import com.cpms.api.com.repository.CustomComCodeRepository;
import com.cpms.common.util.YesNo;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ComCodeRepositoryImpl implements CustomComCodeRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QComCodeDetail comCodeDetail = QComCodeDetail.comCodeDetail;

    @Override
    public List<ResComCodeDTO> selectComCodeList(ReqComCodeDTO reqComCodeDTO) {
        String masterCodeId = reqComCodeDTO.getMasterCodeId();

        return jpaQueryFactory
                .select(new QResComCodeDTO(comCodeDetail.codeId, comCodeDetail.codeNm))
                .from(comCodeDetail)
                .where(
                        comCodeDetail.masterCodeId.eq(masterCodeId),
                        comCodeDetail.useYn.eq(YesNo.Y),
                        comCodeDetail.delYn.eq(YesNo.N))
                .orderBy(comCodeDetail.sortOrder.asc())
                .fetch();
    }
}
