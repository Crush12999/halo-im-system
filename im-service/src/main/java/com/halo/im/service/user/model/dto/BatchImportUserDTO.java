package com.halo.im.service.user.model.dto;

import com.halo.im.common.model.dto.BaseRequestDTO;
import com.halo.im.service.user.dao.entity.ImUserDataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 批量导入用户的实体
 *
 * @author halo
 * @since 2023/4/30 07:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class BatchImportUserDTO extends BaseRequestDTO {

    /**
     * 用户列表
     */
    private List<ImUserDataEntity> userData;

}
