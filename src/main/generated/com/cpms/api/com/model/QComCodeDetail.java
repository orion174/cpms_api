package com.cpms.api.com.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QComCodeDetail is a Querydsl query type for ComCodeDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComCodeDetail extends EntityPathBase<ComCodeDetail> {

    private static final long serialVersionUID = 1069674827L;

    public static final QComCodeDetail comCodeDetail = new QComCodeDetail("comCodeDetail");

    public final StringPath codeId = createString("codeId");

    public final StringPath codeNm = createString("codeNm");

    public final NumberPath<Integer> comCodeId = createNumber("comCodeId", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> delDt = createDateTime("delDt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> delId = createNumber("delId", Integer.class);

    public final EnumPath<com.cpms.common.util.YesNo> delYn = createEnum("delYn", com.cpms.common.util.YesNo.class);

    public final NumberPath<Integer> depth = createNumber("depth", Integer.class);

    public final StringPath groupId = createString("groupId");

    public final StringPath masterCodeId = createString("masterCodeId");

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> regId = createNumber("regId", Integer.class);

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> udtDt = createDateTime("udtDt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> udtId = createNumber("udtId", Integer.class);

    public final EnumPath<com.cpms.common.util.YesNo> useYn = createEnum("useYn", com.cpms.common.util.YesNo.class);

    public QComCodeDetail(String variable) {
        super(ComCodeDetail.class, forVariable(variable));
    }

    public QComCodeDetail(Path<? extends ComCodeDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QComCodeDetail(PathMetadata metadata) {
        super(ComCodeDetail.class, metadata);
    }

}

