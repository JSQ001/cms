package com.eicas.security.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 权限信息表
 *
 * @author osnudt
 * @since 2022-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_permit")
@ApiModel(value="AuthPermit对象", description="权限信息表")
public class AuthPermit implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "父权限ID")
    private Long parentId;

    @ApiModelProperty(value = "权限名称")
    private String permitName;

    @ApiModelProperty(value = "权限标识")
    private String permitKey;

    @ApiModelProperty(value = "请求地址")
    private String requestPath;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "请求参数")
    private String params;

    @ApiModelProperty(value = "权限类型（1-目录 2-菜单 3-按钮）")
    private Integer permitType;

    @ApiModelProperty(value = "显示顺序")
    private Integer sortOrder;

    @ApiModelProperty(value = "权限状态（1-显示 0-隐藏）")
    @TableField("is_visible")
    private boolean visible;

    @ApiModelProperty(value = "是否启用（1-正常 0-停用）")
    @TableField("is_enabled")
    @TableLogic
    private boolean enabled;

    @ApiModelProperty(value = "关联图标")
    private String icon;

    @ApiModelProperty(value = "备注")
    private String remarks;

}
