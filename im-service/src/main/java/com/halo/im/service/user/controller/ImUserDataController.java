package com.halo.im.service.user.controller;

import com.halo.im.common.ResponseVO;
import com.halo.im.service.user.dao.entity.ImUserDataEntity;
import com.halo.im.service.user.model.dto.ModifyUserInfoDTO;
import com.halo.im.service.user.model.dto.UserInfoDTO;
import com.halo.im.service.user.model.vo.UserInfoVO;
import com.halo.im.service.user.service.ImUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息相关接口
 *
 * @author halo
 * @since 2023/4/30 08:53
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user/data")
public class ImUserDataController {

    private final ImUserService imUserService;

    @PostMapping("/get-user-info")
    public ResponseVO<UserInfoVO> getUserInfo(@RequestBody UserInfoDTO req,
                                              @RequestParam Integer appId) {
        req.setAppId(appId);
        return ResponseVO.successResponse(imUserService.getUserInfo(req));
    }

    @GetMapping("/get-single-user-info")
    public ResponseVO<ImUserDataEntity> getSingleUserInfo(@RequestParam(value = "userId") String userId,
                                                          @RequestParam(value = "appId") Integer appId) {
        return ResponseVO.successResponse(imUserService.getSingleUserInfo(userId, appId));
    }

    @PutMapping("/modify-user-info")
    public ResponseVO<Void> modifyUserInfo(@RequestBody @Validated ModifyUserInfoDTO req,
                                           @RequestParam Integer appId) {
        req.setAppId(appId);
        imUserService.modifyUserInfo(req);
        return ResponseVO.successResponse();
    }
}
