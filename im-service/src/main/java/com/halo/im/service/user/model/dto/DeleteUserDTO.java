package com.halo.im.service.user.model.dto;

import com.halo.im.common.model.dto.BaseRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 删除用户 dto
 *
 * @author halo
 * @since 2023/4/30 08:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class DeleteUserDTO extends BaseRequestDTO {

    @NotEmpty(message = "用户id列表不能为空")
    private List<String> userIds;
}
