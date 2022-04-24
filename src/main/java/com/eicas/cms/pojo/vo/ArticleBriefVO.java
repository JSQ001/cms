package com.eicas.cms.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author osnudt
 * @since 2022/4/20
 */

@Data
public class ArticleBriefVO {
    @ApiModelProperty(value = "文章ID")
    private Long id;

    @ApiModelProperty(value = "所属栏目id")
    private Long catalogId;

    @ApiModelProperty(value = "所属栏目名称")
    private String catalogName;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "来源")
    private String source;

    @ApiModelProperty(value = "文章状态：0-草稿,1-待审核,2-审核通过,3-审核不通过")
    private Integer state;

    @ApiModelProperty(value = "是否前台显示：0-不显示，1-显示")
    private Boolean visible;

    @ApiModelProperty(value = "是否焦点图显示")
    private Boolean focus;

    @ApiModelProperty(value = "是否推荐")
    private Boolean recommended;

    @ApiModelProperty(value = "是否置顶")
    private Boolean top;

    @ApiModelProperty(value = "发布时间")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "自定义顺序")
    private Integer sortOrder;

    @ApiModelProperty(value = "点击次数")
    private Integer hitNums;

}
