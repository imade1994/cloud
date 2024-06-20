package com.tj.cloud.core.utils;

import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * * @Author codingMan_tj
 * * @Date 2024/3/26 13:53
 * * @version v1.0.0
 * * @desc
 **/
public class FunctionUtil {

    /**
     * stream nullable
     *
     * @param collection collection
     * @param isParallel is parallel
     * @param <T>        type
     * @return stream
     */
    public static <T> Stream<T> streamOfNullable(Collection<T> collection, boolean isParallel) {
        if (ObjectUtils.isEmpty(collection)) {
            return Stream.empty();
        }
        return isParallel ? collection.parallelStream() : collection.stream();
    }
}
