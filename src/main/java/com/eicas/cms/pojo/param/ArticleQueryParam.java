package com.eicas.cms.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author osnudt
 * @since 2022/4/19
 */

@Data
public class ArticleQueryParam {
    @ApiModelProperty(value = "所属栏目ID")
    private Long catalogId;

    @ApiModelProperty(value = "文章标题")
    private String title;

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

    @ApiModelProperty(value = "发布时间-起始")
    private LocalDateTime beginPublishTime;

    @ApiModelProperty(value = "发布时间-截止")
    private LocalDateTime endPublishTime;

}
