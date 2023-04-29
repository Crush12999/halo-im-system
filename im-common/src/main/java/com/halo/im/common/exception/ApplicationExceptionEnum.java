package com.halo.im.common.exception;

/**
 * 全局异常枚举
 *
 * @author halo
 * @since 2023/4/30 02:06
 */
public interface ApplicationExceptionEnum {

    /**
     * 获取异常 code
     *
     * @return code
     */
    int getCode();

    /**
     * 获取异常信息
     *
     * @return 异常信息
     */
    String getError();
}
