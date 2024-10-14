package com.cpms.api.auth.dto.res;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.cpms.api.auth.dto.res.QResLoginDTO is a Querydsl Projection type for ResLoginDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QResLoginDTO extends ConstructorExpression<ResLoginDTO> {

    private static final long serialVersionUID = 1331174421L;

    public QResLoginDTO(com.querydsl.core.types.Expression<Long> userId, com.querydsl.core.types.Expression<String> authType, com.querydsl.core.types.Expression<Integer> companyId, com.querydsl.core.types.Expression<String> loginId, com.querydsl.core.types.Expression<String> loginPw, com.querydsl.core.types.Expression<com.cpms.common.util.YesNo> useYn) {
        super(ResLoginDTO.class, new Class<?>[]{long.class, String.class, int.class, String.class, String.class, com.cpms.common.util.YesNo.class}, userId, authType, companyId, loginId, loginPw, useYn);
    }

}

