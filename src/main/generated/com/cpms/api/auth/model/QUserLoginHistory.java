package com.cpms.api.auth.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserLoginHistory is a Querydsl query type for UserLoginHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserLoginHistory extends EntityPathBase<UserLoginHistory> {

    private static final long serialVersionUID = -224542977L;

    public static final QUserLoginHistory userLoginHistory = new QUserLoginHistory("userLoginHistory");

    public final StringPath accessIp = createString("accessIp");

    public final NumberPath<Long> loginHistoryId = createNumber("loginHistoryId", Long.class);

    public final StringPath loginId = createString("loginId");

    public final StringPath refreshToken = createString("refreshToken");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUserLoginHistory(String variable) {
        super(UserLoginHistory.class, forVariable(variable));
    }

    public QUserLoginHistory(Path<? extends UserLoginHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserLoginHistory(PathMetadata metadata) {
        super(UserLoginHistory.class, metadata);
    }

}

