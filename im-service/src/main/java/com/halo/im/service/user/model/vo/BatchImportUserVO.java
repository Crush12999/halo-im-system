package com.halo.im.service.user.model.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 批量导入用户信息 VO
 *
 * @author halo
 * @since 2023/4/30 07:43
 */
@Data
@ToString
public class BatchImportUserVO {

    /**
     * 导入成功的用户 id 列表
     */
    private List<String> successId;

    /**
     * 导入失败的用户 id 列表
     */
    private List<String> errorId;
}
