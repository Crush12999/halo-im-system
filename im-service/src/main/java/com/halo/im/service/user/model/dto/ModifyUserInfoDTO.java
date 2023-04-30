package com.halo.im.service.user.model.dto;

import com.halo.im.common.model.dto.BaseRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * 修改用户信息 dto
 *
 * @author halo
 * @since 2023/4/30 08:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class ModifyUserInfoDTO extends BaseRequestDTO {

    /**
     * 用户id
     */
    @NotEmpty(message = "用户id不能为空")
    private String userId;

    /**
     * 用户名称
     */
    private String nickName;

    /**
     * 地址
     */
    private String location;

    /**
     * 生日
     */
    private String birthDay;

    /**
     * 授权码
     */
    private String password;

    /**
     * 头像
     */
    private String photo;

    /**
     * 性别
     */
    private Integer userSex;

    /**
     * 个性签名
     */
    private String selfSignature;

    /**
     * 加好友验证类型（Friend_AllowType） 1需要验证
     */
    private Integer friendAllowType;

    /**
     * 扩展信息
     */
    private String extra;

}
