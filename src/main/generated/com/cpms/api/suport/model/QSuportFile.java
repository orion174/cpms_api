package com.cpms.api.suport.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSuportFile is a Querydsl query type for SuportFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSuportFile extends EntityPathBase<SuportFile> {

    private static final long serialVersionUID = -2060979955L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSuportFile suportFile = new QSuportFile("suportFile");

    public final DateTimePath<java.time.LocalDateTime> delDt = createDateTime("delDt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> delId = createNumber("delId", Integer.class);

    public final EnumPath<com.cpms.common.util.YesNo> delYn = createEnum("delYn", com.cpms.common.util.YesNo.class);

    public final StringPath fileCategory = createString("fileCategory");

    public final StringPath fileExt = createString("fileExt");

    public final StringPath fileNm = createString("fileNm");

    public final StringPath fileOgNm = createString("fileOgNm");

    public final StringPath filePath = createString("filePath");

    public final NumberPath<Long> fileSize = createNumber("fileSize", Long.class);

    public final DateTimePath<java.time.LocalDateTime> regDt = createDateTime("regDt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> regId = createNumber("regId", Integer.class);

    public final NumberPath<Integer> suportFileId = createNumber("suportFileId", Integer.class);

    public final QSuportReq suportReq;

    public final DateTimePath<java.time.LocalDateTime> udtDt = createDateTime("udtDt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> udtId = createNumber("udtId", Integer.class);

    public QSuportFile(String variable) {
        this(SuportFile.class, forVariable(variable), INITS);
    }

    public QSuportFile(Path<? extends SuportFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSuportFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSuportFile(PathMetadata metadata, PathInits inits) {
        this(SuportFile.class, metadata, inits);
    }

    public QSuportFile(Class<? extends SuportFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.suportReq = inits.isInitialized("suportReq") ? new QSuportReq(forProperty("suportReq")) : null;
    }

}

