package com.example.common.result;

/**
 * 错误码契约
 *
 * <p>错误码为五位字符串，结构为 A-BB-CC：A 表示错误类型（0 成功、1 系统、2 业务、3 第三方），
 * BB 表示模块编号，CC 表示模块内序号。错误码仅描述业务结果，与 HTTP 状态码无对应关系
 *
 * @since 1.0.0
 */
public interface ErrorCode {

    /**
     * 五位错误码
     *
     * @return 错误码
     */
    String getCode();

    /**
     * 默认提示信息
     *
     * @return 提示信息
     */
    String getMessage();
}
