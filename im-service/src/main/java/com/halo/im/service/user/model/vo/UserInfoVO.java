package com.halo.im.service.user.model.vo;

import com.halo.im.service.user.dao.entity.ImUserDataEntity;
import lombok.Data;

import java.util.List;

/**
 * 用户信息 vo 对象
 *
 * @author halo
 * @since 2023/4/30 08:24
 */
@Data
public class UserInfoVO {

    /**
     * 每个用户的用户信息
     */
    List<ImUserDataEntity> userDataItem;

    /**
     * 获取失败的用户id列表
     */
    List<String> failUser;

}
