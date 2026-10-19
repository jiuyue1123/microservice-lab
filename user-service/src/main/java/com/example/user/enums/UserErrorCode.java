package com.example.user.enums;

import com.example.common.result.ErrorCode;

/**
 * 用户模块错误码，模块编号 01
 */
public enum UserErrorCode implements ErrorCode {

    /**
     * 用户不存在或已删除
     */
    USER_NOT_FOUND("20101", "用户不存在"),

    /**
     * 手机号已被未删除用户占用
     */
    PHONE_ALREADY_EXISTS("20102", "手机号已被使用"),

    /**
     * 邮箱已被未删除用户占用
     */
    EMAIL_ALREADY_EXISTS("20103", "邮箱已被使用"),

    /**
     * 乐观锁版本冲突
     */
    USER_VERSION_CONFLICT("20104", "用户信息已被修改，请刷新后重试"),

    /**
     * 当前用户状态不允许该操作
     */
    USER_STATE_NOT_ALLOWED("20105", "当前用户状态不允许该操作");

    private final String code;

    private final String message;

    UserErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
