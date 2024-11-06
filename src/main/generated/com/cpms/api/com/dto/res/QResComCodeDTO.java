package com.cpms.api.com.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.cpms.api.com.dto.res.QResComCodeDTO is a Querydsl Projection type for ResComCodeDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QResComCodeDTO extends ConstructorExpression<ResComCodeDTO> {

    private static final long serialVersionUID = -207789147L;

    public QResComCodeDTO(com.querydsl.core.types.Expression<String> codeId, com.querydsl.core.types.Expression<String> codeNm) {
        super(ResComCodeDTO.class, new Class<?>[]{String.class, String.class}, codeId, codeNm);
    }

}

