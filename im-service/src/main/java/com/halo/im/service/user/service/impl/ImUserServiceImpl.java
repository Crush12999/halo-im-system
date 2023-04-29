package com.halo.im.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.halo.im.service.user.dao.entity.ImUserDataEntity;
import com.halo.im.service.user.dao.mapper.ImUserDataMapper;
import com.halo.im.service.user.service.ImUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
