package com.halo.im.common.enums;

/**
 * 逻辑删除枚举
 *
 * @author halo
 * @since 2023/4/30 08:38
 */
public enum DelFlagEnum {

    /**
     * 0 正常；1 删除。
     */
    NORMAL(0),

    DELETE(1),
    ;

    private final int code;

    DelFlagEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
