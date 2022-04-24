package com.eicas.security.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 系统账号表
 *
 * @author osnudt
 * @since 2022-04-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_user")
@ApiModel(value="AuthUser对象", description="系统账号表")
public class AuthUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "统一认证中心用户ID")
    private Long uuid;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户密码")
    @TableField("passwd")
    private String password;

    @ApiModelProperty(value = "盐值")
    private String salt;

    @ApiModelProperty(value = "账号是否可用。默认为1（可用）")
    @TableField("is_enabled")
    private boolean enabled;

    @ApiModelProperty(value = "是否过期。默认为1（没有过期）")
    @TableField("is_account_non_expired")
    private boolean accountNonExpired;

    @ApiModelProperty(value = "账号是否锁定。默认为1（没有锁定）")
    @TableField("is_account_non_locked")
    private boolean accountNonLocked;

    @ApiModelProperty(value = "证书（密码）是否过期。默认为1（没有过期）")
    @TableField("is_credentials_non_expired")
    private boolean credentialsNotExpired;

    @ApiModelProperty(value = "姓名")
    private String realname;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "出生日期")
    private LocalDate birthday;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "头像")
    private String portrait;

    @ApiModelProperty(value = "人员类别")
    private Integer type;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "联系方式")
    private String contact;

    @ApiModelProperty(value = "民族")
    private String ethnic;

    @ApiModelProperty(value = "所属组织机构")
    private String organization;

    @ApiModelProperty(value = "职务")
    private String post;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    @TableField("is_deleted")
    private boolean deleted;
}
