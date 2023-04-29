package com.halo.im.common;

import com.halo.im.common.exception.ApplicationExceptionEnum;

/**
 * 基础错误码枚举
 *
 * @author halo
 * @since 2023/4/30 02:08
 */
public enum BaseErrorCode implements ApplicationExceptionEnum {

    /**
     * 成功状态
     */
    SUCCESS(200, "success"),

    /**
     * 服务内部错误
     */
    SYSTEM_ERROR(90000, "服务器内部错误, 请联系管理员"),

    /**
     * 参数校验错误
     */
    PARAMETER_ERROR(90001, "参数校验错误"),

    ;

    private final int code;

    private final String error;

    BaseErrorCode(int code, String error) {
        this.code = code;
        this.error = error;
    }

    /**
     * 获取异常 code
     *
     * @return code
     */
    @Override
    public int getCode() {
        return this.code;
    }

    /**
     * 获取异常信息
     *
     * @return 异常信息
     */
    @Override
    public String getError() {
        return this.error;
    }
}
