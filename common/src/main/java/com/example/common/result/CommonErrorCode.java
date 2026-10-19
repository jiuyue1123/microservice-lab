package com.example.common.result;

/**
 * 通用错误码
 *
 * <p>模块编号固定为 00。业务模块应定义自己的错误码枚举并实现 {@link ErrorCode}，
 * 模块编号需全局唯一
 *
 * @since 1.0.0
 */
public enum CommonErrorCode implements ErrorCode {

    SUCCESS("00000", "操作成功"),

    SYSTEM_ERROR("10000", "系统内部错误"),
    PARAM_INVALID("10001", "请求参数错误"),
    UNAUTHORIZED("10002", "未认证"),
    FORBIDDEN("10003", "无访问权限"),
    RESOURCE_NOT_FOUND("10004", "资源不存在"),
    METHOD_NOT_ALLOWED("10005", "请求方法不支持"),
    REQUEST_TOO_FREQUENT("10006", "请求过于频繁"),
    SERVICE_UNAVAILABLE("10007", "服务暂不可用"),
    DATA_CONFLICT("10008", "数据已存在"),

    BUSINESS_ERROR("20000", "业务处理失败");

    private final String code;

    private final String message;

    CommonErrorCode(String code, String message) {
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
