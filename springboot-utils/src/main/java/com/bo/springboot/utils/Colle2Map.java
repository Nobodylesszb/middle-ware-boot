package com.bo.springboot.utils;

import com.google.common.collect.Maps;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @auther: bo
 * @Date: 2021/3/31 19:51
 * @version: 集合转对象
 * @description:
 */
public class Colle2Map {

    /**
     * collection转换为map
     *
     * @param collection      列表
     * @param beforePredicate 转换前过滤项
     * @param keyMapper       function将原对象转换为map的key
     * @param valueMapper     function将原对象转换为map的value
     * @param <T>             map的key类型
     * @param <R>             map的value类型
     * @param <E>             原始对象类型
     * @return
     */
    public static <T, R, E> Map<T, R> convertToMap(Collection<E> collection, Predicate<E> beforePredicate, Function<E, T> keyMapper, Function<E, R> valueMapper) {
        if (isEmpty(collection)) {
            return Maps.newHashMap();
        }

        return collection.stream()
                .filter(Objects::nonNull)
                .filter(beforePredicate)
                .distinct()
                .collect(
                        Collectors.toMap(
                                keyMapper,
                                valueMapper,
                                (left, right) -> right
                        )
                );
    }


    /**
     * 去重
     *
     * @return
     */
    private <T, R> List<T> distinct(List<T> tList, Function<? super T, R> function) {
        List<T> result = new ArrayList<>();
        Set<R> set = ConcurrentHashMap.newKeySet();

        for (T t : tList) {
            R r = function.apply(t);
            if (!set.contains(r)) {
                set.add(r);
                result.add(t);
            }
        }

        return result;

    }
}
