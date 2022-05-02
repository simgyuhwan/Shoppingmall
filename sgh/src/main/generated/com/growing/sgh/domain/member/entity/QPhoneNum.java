package com.growing.sgh.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPhoneNum is a Querydsl query type for PhoneNum
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPhoneNum extends BeanPath<PhoneNum> {

    private static final long serialVersionUID = 2002610899L;

    public static final QPhoneNum phoneNum = new QPhoneNum("phoneNum");

    public final StringPath PhoneNum1 = createString("PhoneNum1");

    public final StringPath PhoneNum2 = createString("PhoneNum2");

    public final StringPath PhoneNum3 = createString("PhoneNum3");

    public QPhoneNum(String variable) {
        super(PhoneNum.class, forVariable(variable));
    }

    public QPhoneNum(Path<? extends PhoneNum> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPhoneNum(PathMetadata metadata) {
        super(PhoneNum.class, metadata);
    }

}

