package com.halo.im.service.user.model.dto;

import com.halo.im.common.model.dto.BaseRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户信息请求实体
 *
 * @author halo
 * @since 2023/4/30 08:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfoDTO extends BaseRequestDTO {

    /**
     * 用户 id 列表
     */
    private List<String> userIds;
}
