package com.cpms.api.suport.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSuportReq is a Querydsl query type for SuportReq
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSuportReq extends EntityPathBase<SuportReq> {

    private static final long serialVersionUID = -482113811L;

    public static final QSuportReq suportReq = new QSuportReq("suportReq");

    public final DateTimePath<java.time.LocalDateTime> delDt = createDateTime("delDt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> delId = createNumber("delId", Integer.class);

    public final EnumPath<com.cpms.common.util.YesNo> delYn = createEnum("delYn", com.cpms.common.util.YesNo.class);

    public final ListPath<SuportFile, QSuportFile> files = this.<SuportFile, QSuportFile>createList("files", SuportFile.class, QSuportFile.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> regId = createNumber("regId", Integer.class);

    public final NumberPath<Integer> reqCompanyId = createNumber("reqCompanyId", Integer.class);

    public final DatePath<java.time.LocalDate> reqDate = createDate("reqDate", java.time.LocalDate.class);

    public final NumberPath<Integer> reqProjectId = createNumber("reqProjectId", Integer.class);

    public final StringPath requestCd = createString("requestCd");

    public final DatePath<java.time.LocalDate> resDate = createDate("resDate", java.time.LocalDate.class);

    public final NumberPath<Integer> resUserId = createNumber("resUserId", Integer.class);

    public final StringPath statusCd = createString("statusCd");

    public final StringPath suportEditor = createString("suportEditor");

    public final NumberPath<Integer> suportReqId = createNumber("suportReqId", Integer.class);

    public final StringPath suportTitle = createString("suportTitle");

    public final DateTimePath<java.time.LocalDateTime> udtDt = createDateTime("udtDt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> udtId = createNumber("udtId", Integer.class);

    public final NumberPath<Integer> userCompanyId = createNumber("userCompanyId", Integer.class);

    public QSuportReq(String variable) {
        super(SuportReq.class, forVariable(variable));
    }

    public QSuportReq(Path<? extends SuportReq> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSuportReq(PathMetadata metadata) {
        super(SuportReq.class, metadata);
    }

}

