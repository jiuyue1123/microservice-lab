package com.example.common.result;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应结果
 *
 * @param pageNumber 当前页码，从 1 开始
 * @param pageSize   每页条数
 * @param total      总记录数
 * @param records    当前页记录
 * @param <T>        记录类型
 * @since 1.0.0
 */
public record PageResult<T>(long pageNumber, long pageSize, long total, List<T> records)
        implements Serializable {

    public PageResult {
        records = records == null ? List.of() : List.copyOf(records);
    }

    public static <T> PageResult<T> of(long pageNumber, long pageSize, long total, List<T> records) {
        return new PageResult<>(pageNumber, pageSize, total, records);
    }

    public static <T> PageResult<T> empty(long pageNumber, long pageSize) {
        return new PageResult<>(pageNumber, pageSize, 0, List.of());
    }
}
