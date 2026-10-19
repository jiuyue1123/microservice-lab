package com.example.common.exception;

import com.example.common.result.ErrorCode;

/**
 * 业务异常
 *
 * <p>错误码与提示信息均来自 {@link ErrorCode}，异常本身不承载额外的错误语义
 *
 * @since 1.0.0
 */
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
