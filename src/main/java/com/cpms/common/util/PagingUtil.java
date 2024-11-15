package com.cpms.common.util;

import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PagingUtil {
    public OrderSpecifier<?>[] getOrderBy(
            final Sort sort, final Class<?> className, final String entityName) {
        return sort.stream()
                .map(
                        order ->
                                new OrderSpecifier<>(
                                        order.isAscending() ? Order.ASC : Order.DESC,
                                        Expressions.stringPath(
                                                String.valueOf(
                                                        new PathBuilder<>(className, entityName)
                                                                .get(order.getProperty())))))
                .toArray(OrderSpecifier[]::new);
    }
}
