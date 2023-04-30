package com.halo.im.service.user.controller;

import com.halo.im.common.ResponseVO;
import com.halo.im.service.user.model.dto.BatchImportUserDTO;
import com.halo.im.service.user.model.dto.DeleteUserDTO;
import com.halo.im.service.user.model.vo.BatchImportUserVO;
import com.halo.im.service.user.service.ImUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * im 用户相关接口
 *
 * @author halo
 * @since 2023/4/30 07:48
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user")
public class ImUserController {

    private final ImUserService imUserService;

    /**
     * 批量导入用户
     */
    @PostMapping("/batch-import")
    public ResponseVO<BatchImportUserVO> batchImportUser(@RequestBody BatchImportUserDTO batchImportUserDTO,
                                                         @RequestParam(required = true) Integer appId) {
        batchImportUserDTO.setAppId(appId);
        return ResponseVO.successResponse(imUserService.batchImportUser(batchImportUserDTO));
    }

    @DeleteMapping("/batch-delete")
    public ResponseVO<BatchImportUserVO> deleteUser(@RequestBody @Validated DeleteUserDTO deleteUserDTO,
                                                    @RequestParam(required = true) Integer appId) {
        deleteUserDTO.setAppId(appId);
        return ResponseVO.successResponse(imUserService.deleteUser(deleteUserDTO));
    }
}
