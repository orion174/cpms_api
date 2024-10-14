package com.cpms.api.auth.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCpmsUser is a Querydsl query type for CpmsUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCpmsUser extends EntityPathBase<CpmsUser> {

    private static final long serialVersionUID = 311684327L;

    public static final QCpmsUser cpmsUser = new QCpmsUser("cpmsUser");

    public final StringPath authType = createString("authType");

    public final NumberPath<Integer> companyId = createNumber("companyId", Integer.class);

    public final EnumPath<com.cpms.common.util.YesNo> delYn = createEnum("delYn", com.cpms.common.util.YesNo.class);

    public final StringPath loginId = createString("loginId");

    public final StringPath loginPw = createString("loginPw");

    public final StringPath userDept = createString("userDept");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath userInfo = createString("userInfo");

    public final StringPath userNm = createString("userNm");

    public final StringPath userPhone = createString("userPhone");

    public final StringPath userPos = createString("userPos");

    public final EnumPath<com.cpms.common.util.YesNo> useYn = createEnum("useYn", com.cpms.common.util.YesNo.class);

    public QCpmsUser(String variable) {
        super(CpmsUser.class, forVariable(variable));
    }

    public QCpmsUser(Path<? extends CpmsUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCpmsUser(PathMetadata metadata) {
        super(CpmsUser.class, metadata);
    }

}

