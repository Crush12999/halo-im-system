package com.halo.im.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.halo.im.service.user.dao.entity.ImUserDataEntity;
import com.halo.im.service.user.model.dto.BatchImportUserDTO;
import com.halo.im.service.user.model.dto.DeleteUserDTO;
import com.halo.im.service.user.model.dto.ModifyUserInfoDTO;
import com.halo.im.service.user.model.dto.UserInfoDTO;
import com.halo.im.service.user.model.vo.BatchImportUserVO;
import com.halo.im.service.user.model.vo.UserInfoVO;

/**
 * UserService
 *
 * @author halo
 * @since 2023/4/30 02:20
 */
public interface ImUserService extends IService<ImUserDataEntity> {

    /**
     * 批量导入用户
     *
     * @param batchImportUserDTO 导入的用户信息dto
     * @return 导入结果
     */
    BatchImportUserVO batchImportUser(BatchImportUserDTO batchImportUserDTO);

    /**
     * 批量获取用户信息
     *
     * @param userInfoDTO 待获取信息的用户 id 列表
     * @return 用户信息
     */
    UserInfoVO getUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 获取单个用户信息
     *
     * @param userId 用户id
     * @param appId  应用id
     * @return 用户信息
     */
    ImUserDataEntity getSingleUserInfo(String userId, Integer appId);

    /**
     * 批量删除用户
     *
     * @param deleteUserDTO 待删除用户的id列表
     * @return 删除过程信息
     */
    BatchImportUserVO deleteUser(DeleteUserDTO deleteUserDTO);

    /**
     * 修改用户信息
     *
     * @param modifyUserInfoDTO 修改用户信息
     */
    void modifyUserInfo(ModifyUserInfoDTO modifyUserInfoDTO);
}
