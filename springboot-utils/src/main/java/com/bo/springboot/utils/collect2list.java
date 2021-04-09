package com.bo.springboot.utils;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @auther: bo
 * @Date: 2021/4/9 20:57
 * @version:
 * @description:
 */
public class collect2list {

    /**
     * 原对象列表转换成另外一个对象列表
     * @param collection        列表
     * @param beforePredicate   转换前过滤项
     * @param mapper            转换的类型
     * @param <T>               原始对象类型
     * @param <R>               转换后的对象类型
     * @return
     */
    public static <T, R> List<R> convertToList(Collection<T> collection, Predicate<T> beforePredicate, Function<T, R> mapper) {
        if (isEmpty(collection)) {
            return Lists.newArrayList();
        }

        return collection.stream()
                .filter(Objects::nonNull)
                .filter(beforePredicate)
                .map(mapper)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }
}
