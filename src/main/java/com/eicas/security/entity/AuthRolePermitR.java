package com.eicas.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色和权限关联表
 *
 * @author osnudt
 * @since 2022-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_role_permit_r")
@ApiModel(value="AuthRolePermitR对象", description="角色和权限关联表")
public class AuthRolePermitR implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "role_id", type = IdType.ASSIGN_ID)
    private Long roleId;

    @ApiModelProperty(value = "角色ID")
    private Long permitId;
}
