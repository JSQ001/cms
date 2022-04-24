package com.eicas.cms.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 栏目信息表
 *
 * @author osnudt
 * @since 2022-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cms_catalog")
@ApiModel(value="Catalog对象", description="栏目信息表")
public class CatalogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "所属父栏目id")
    private Long parentId;

    @ApiModelProperty(value = "栏目层级关系")
    private String treeRel;

    @ApiModelProperty(value = "栏目编码")
    private String code;

    @ApiModelProperty(value = "栏目名称")
    private String catalogName;

    @ApiModelProperty(value = "栏目封面图片地址")
    private String coverImgUrl;

    @ApiModelProperty(value = "栏目标记属性（扩展用）")
    private String flag;

    @ApiModelProperty(value = "栏目路径")
    private String path;

    @ApiModelProperty(value = "栏目类型：0-内部栏目，1-外部栏目")
    private Integer type;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "自定义链接")
    private String customUrl;

    @ApiModelProperty(value = "栏目上线时间")
    @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "内容页模板")
    private String contentTemplateUrl;

    @ApiModelProperty(value = "列表页模板")
    private String listTemplateUrl;

    @ApiModelProperty(value = "自定义顺序")
    private Integer sortOrder;

    @ApiModelProperty(value = "是否前台可见")
    @TableField("is_visible")
    private Boolean visible;

    @ApiModelProperty(value = "是否允许投稿")
    @TableField("is_allowed_submit")
    private Boolean allowedSubmit;

    @ApiModelProperty(value = "栏目描述")
    private String remarks;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    @TableField("is_deleted")
    private Boolean deleted;

    @TableField(exist = false)
    private List<CatalogEntity> children;
}
