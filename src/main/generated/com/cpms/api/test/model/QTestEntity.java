package com.cpms.api.test.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTestEntity is a Querydsl query type for TestEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTestEntity extends EntityPathBase<TestEntity> {

    private static final long serialVersionUID = -29024556L;

    public static final QTestEntity testEntity = new QTestEntity("testEntity");

    public final StringPath userAuthCd = createString("userAuthCd");

    public final StringPath userId = createString("userId");

    public final NumberPath<Integer> userMasterId = createNumber("userMasterId", Integer.class);

    public final StringPath userNm = createString("userNm");

    public QTestEntity(String variable) {
        super(TestEntity.class, forVariable(variable));
    }

    public QTestEntity(Path<? extends TestEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTestEntity(PathMetadata metadata) {
        super(TestEntity.class, metadata);
    }

}

