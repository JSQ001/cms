package com.eicas.security.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author osnudt
 * @since 2022/4/23
 */

@Data
public class AuthUserInfoVO {
    @ApiModelProperty(value = "账号ID")
    private Long id;

    @ApiModelProperty(value = "统一认证中心用户ID")
    private Long uuid;

    @ApiModelProperty(value = "用户名")
    private String username;

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
}
