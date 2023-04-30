package com.halo.im.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.im.common.BaseErrorCode;
import com.halo.im.common.enums.DelFlagEnum;
import com.halo.im.common.enums.UserErrorCode;
import com.halo.im.common.exception.ApplicationException;
import com.halo.im.service.user.dao.entity.ImUserDataEntity;
import com.halo.im.service.user.dao.mapper.ImUserDataMapper;
import com.halo.im.service.user.model.dto.BatchImportUserDTO;
import com.halo.im.service.user.model.dto.DeleteUserDTO;
import com.halo.im.service.user.model.dto.ModifyUserInfoDTO;
import com.halo.im.service.user.model.dto.UserInfoDTO;
import com.halo.im.service.user.model.vo.BatchImportUserVO;
import com.halo.im.service.user.model.vo.UserInfoVO;
import com.halo.im.service.user.service.ImUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * UserServiceImpl
 *
 * @author halo
 * @since 2023/4/30 02:21
 */
@Slf4j
@Service
public class ImUserServiceImpl extends ServiceImpl<ImUserDataMapper, ImUserDataEntity>
        implements ImUserService {

    /**
     * 批量导入用户
     *
     * @param batchImportUserDTO 导入的用户信息dto
     * @return 导入结果
     */
    @Override
    public BatchImportUserVO batchImportUser(BatchImportUserDTO batchImportUserDTO) {

        if (batchImportUserDTO.getUserData().size() > 100) {
            throw new ApplicationException(UserErrorCode.IMPORT_SIZE_BEYOND);
        }

        List<String> successId = new ArrayList<>();
        List<String> errorId = new ArrayList<>();

        batchImportUserDTO.getUserData().forEach(e -> {
            try {
                e.setAppId(batchImportUserDTO.getAppId());
                int insert = baseMapper.insert(e);
                if (insert == 1) {
                    successId.add(e.getUserId());
                }
            } catch (Exception ex) {
                log.error("User import is abnormal: [{}]", ex.getMessage(), ex);
                errorId.add(e.getUserId());
            }
        });

        BatchImportUserVO batchImportUserVO = new BatchImportUserVO();
        batchImportUserVO.setSuccessId(successId);
        batchImportUserVO.setErrorId(errorId);

        return batchImportUserVO;
    }

    /**
     * 批量获取用户信息
     *
     * @param userInfoDTO 待获取信息的用户 id 列表
     * @return 用户信息
     */
    @Override
    public UserInfoVO getUserInfo(UserInfoDTO userInfoDTO) {
        QueryWrapper<ImUserDataEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_id", userInfoDTO.getAppId());
        queryWrapper.in("user_id", userInfoDTO.getUserIds());
        queryWrapper.eq("del_flag", DelFlagEnum.NORMAL.getCode());

        List<ImUserDataEntity> userDataEntities = baseMapper.selectList(queryWrapper);
        HashMap<String, ImUserDataEntity> map = new HashMap<>(16);

        for (ImUserDataEntity data : userDataEntities) {
            map.put(data.getUserId(), data);
        }

        List<String> failUser = new ArrayList<>();
        for (String uid : userInfoDTO.getUserIds()) {
            if (!map.containsKey(uid)) {
                failUser.add(uid);
            }
        }

        UserInfoVO resp = new UserInfoVO();
        resp.setUserDataItem(userDataEntities);
        resp.setFailUser(failUser);

        return resp;
    }

    /**
     * 获取单个用户信息
     *
     * @param userId 用户id
     * @param appId  应用id
     * @return 用户信息
     */
    @Override
    public ImUserDataEntity getSingleUserInfo(String userId, Integer appId) {
        if (!StringUtils.hasText(userId) || appId == null || appId < 1) {
            throw new ApplicationException(BaseErrorCode.PARAMETER_ERROR);
        }
        QueryWrapper<ImUserDataEntity> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("app_id", appId);
        objectQueryWrapper.eq("user_id", userId);
        objectQueryWrapper.eq("del_flag", DelFlagEnum.NORMAL.getCode());

        ImUserDataEntity imUserDataEntity = baseMapper.selectOne(objectQueryWrapper);

        if (imUserDataEntity == null) {
            throw new ApplicationException(UserErrorCode.USER_IS_NOT_EXIST);
        }
        return imUserDataEntity;
    }

    /**
     * 批量删除用户
     *
     * @param deleteUserDTO 待删除用户的id列表
     * @return 删除过程信息
     */
    @Override
    public BatchImportUserVO deleteUser(DeleteUserDTO deleteUserDTO) {
        ImUserDataEntity entity = new ImUserDataEntity();
        entity.setDelFlag(DelFlagEnum.DELETE.getCode());

        List<String> errorId = new ArrayList<>();
        List<String> successId = new ArrayList<>();

        for (String userId : deleteUserDTO.getUserIds()) {
            QueryWrapper<ImUserDataEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("app_id", deleteUserDTO.getAppId());
            wrapper.eq("user_id", userId);
            wrapper.eq("del_flag", DelFlagEnum.NORMAL.getCode());
            int update = 0;
            try {
                update = baseMapper.update(entity, wrapper);
                if (update > 0) {
                    successId.add(userId);
                } else {
                    errorId.add(userId);
                }
            } catch (Exception ex) {
                log.error("用户 [{}] 删除失败！", userId, ex);
                errorId.add(userId);
            }
        }

        BatchImportUserVO resp = new BatchImportUserVO();
        resp.setSuccessId(successId);
        resp.setErrorId(errorId);

        return resp;
    }

    /**
     * 修改用户信息
     *
     * @param modifyUserInfoDTO 修改用户信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyUserInfo(ModifyUserInfoDTO modifyUserInfoDTO) {
        QueryWrapper<ImUserDataEntity> query = new QueryWrapper<>();
        query.eq("app_id", modifyUserInfoDTO.getAppId());
        query.eq("user_id", modifyUserInfoDTO.getUserId());
        query.eq("del_flag", DelFlagEnum.NORMAL.getCode());
        ImUserDataEntity user = baseMapper.selectOne(query);
        if (user == null) {
            throw new ApplicationException(UserErrorCode.USER_IS_NOT_EXIST);
        }

        ImUserDataEntity update = new ImUserDataEntity();
        BeanUtils.copyProperties(modifyUserInfoDTO, update);

        update.setAppId(null);
        update.setUserId(null);
        int update1 = baseMapper.update(update, query);

        if (update1 == 1) {
            return;
        }
        throw new ApplicationException(UserErrorCode.MODIFY_USER_ERROR);
    }
}
