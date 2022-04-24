package com.eicas.crawler.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 采集文章信息表
 *
 * @author osnudt
 * @since 2022-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cms_collect_article")
@ApiModel(value="CollectArticle对象", description="采集文章信息表")
public class CollectArticleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "入库栏目id")
    private Long catalogId;

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

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "原文网址")
    private String originUrl;

    @ApiModelProperty(value = "原文发布时间")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "采集来源")
    private String source;

    @ApiModelProperty(value = "采集时间")
    private LocalDateTime collectTime;

    @ApiModelProperty(value = "是否已入库")
    @TableField("is_received")
    private Boolean received;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    @TableField("is_deleted")
    private Boolean deleted;
}
