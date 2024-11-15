package com.cpms.api.code.repository.impl;

import java.util.List;
import java.util.Optional;

import com.cpms.api.code.dto.req.ReqComCodeDTO;
import com.cpms.api.code.dto.res.QResComCodeDTO;
import com.cpms.api.code.dto.res.ResComCodeDTO;
import com.cpms.api.code.model.ComCodeDetail;
import com.cpms.api.code.model.QComCodeDetail;
import com.cpms.api.code.repository.CustomComCodeRepository;
import com.cpms.common.helper.YesNo;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

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
                .where(comCodeDetail.masterCodeId.eq(masterCodeId), comCodeDetail.useYn.eq(YesNo.Y))
                .orderBy(comCodeDetail.sortOrder.asc())
                .fetch();
    }

    @Override
    public Optional<ComCodeDetail> findByMasterCodeIdAndCodeId(String masterCodeId, String codeId) {
        ComCodeDetail result =
                jpaQueryFactory
                        .selectFrom(comCodeDetail)
                        .where(
                                comCodeDetail
                                        .masterCodeId
                                        .eq(masterCodeId)
                                        .and(comCodeDetail.codeId.eq(codeId)))
                        .fetchOne();

        return Optional.ofNullable(result);
    }
}
