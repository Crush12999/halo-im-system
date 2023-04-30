package com.halo.im.common.model.dto;

import lombok.Data;

/**
 * 基础请求信息
 *
 * @author halo
 * @since 2023/4/30 07:31
 */
@Data
public class BaseRequestDTO {

    /**
     * 应用 id
     */
    private Integer appId;

    /**
     * 操作
     */
    private String operator;

    /**
     * 客户端类型
     */
    private Integer clientType;

    /**
     * 移动设备识别码 International Mobile Equipment Identity
     */
    private String imei;

}
