package com.eicas.cms.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 文章信息表
 *
 * @author osnudt
 * @since 2022-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cms_article")
@ApiModel(value="Article对象", description="文章信息表")
public class ArticleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "所属栏目id")
    @NotNull(message = "栏目id不能为空")
    private Long catalogId;

    @ApiModelProperty(value = "栏目层级关系")
    private String catalogEncode;

    @ApiModelProperty(value = "栏目名称")
    private String catalogName;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "副标题")
    private String subTitle;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "文章摘要")
    private String essential;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "标题图片")
    private String coverImgUrl;

    @ApiModelProperty(value = "跳转链接地址")
    private String linkUrl;

    @ApiModelProperty(value = "类型（扩展使用）")
    private String type;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "原文网址")
    private String originUrl;

    @ApiModelProperty(value = "来源")
    private String source;

    @ApiModelProperty(value = "描述")
    private String remarks;

    @ApiModelProperty(value = "文章状态：0-草稿,1-待审核,2-审核通过,3-审核不通过")
    private Integer state;

    @ApiModelProperty(value = "是否前台显示：0-不显示，1-显示")
    @TableField("is_visible")
    private Boolean visible;

    @ApiModelProperty(value = "是否焦点图显示")
    @TableField("is_focus")
    private Boolean focus;

    @ApiModelProperty(value = "是否推荐")
    @TableField("is_recommended")
    private Boolean recommended;

    @ApiModelProperty(value = "是否置顶")
    @TableField("is_top")
    private Boolean top;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "自定义顺序")
    private Integer sortOrder;

    @ApiModelProperty(value = "点击次数")
    private Integer hitNums;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    @TableField("is_deleted")
    private Boolean deleted;
}
