package com.example.common.result;

import java.io.Serializable;

/**
 * 统一响应结果
 *
 * @param errorCode 五位错误码，成功为 {@code 00000}
 * @param message   提示信息
 * @param data      响应数据
 * @param <T>       数据类型
 * @since 1.0.0
 */
public record Result<T>(String errorCode, String message, T data) implements Serializable {

    public static <T> Result<T> success(T data) {
        return new Result<>(CommonErrorCode.SUCCESS.getCode(),
                CommonErrorCode.SUCCESS.getMessage(), data);
    }

    public static Result<Void> success() {
        return success(null);
    }

    public static <T> Result<T> failure(ErrorCode errorCode) {
        return failure(errorCode.getCode(), errorCode.getMessage());
    }

    public static <T> Result<T> failure(ErrorCode errorCode, String message) {
        return failure(errorCode.getCode(), message);
    }

    public static <T> Result<T> failure(String errorCode, String message) {
        return new Result<>(errorCode, message, null);
    }
}
