package com.eicas.security.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色信息表
 *
 * @author osnudt
 * @since 2022-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_role")
@ApiModel(value="AuthRole对象", description="角色信息表")
public class AuthRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色权限字符串")
    private String roleKey;

    @ApiModelProperty(value = "显示顺序")
    private Integer sortOrder;

    @ApiModelProperty(value = "数据范围（1-全部数据权限 2-自定数据权限...）")
    private Integer dataScope;

    @ApiModelProperty(value = "是否启用（1-正常 0-停用）")
    @TableField("is_enabled")
    private boolean enabled;

    @ApiModelProperty(value = "角色说明")
    private String remarks;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    @TableField("is_deleted")
    private boolean deleted;
}
