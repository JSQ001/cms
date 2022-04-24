package com.eicas.cms.pojo.param;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author osnudt
 * @since 2022/4/19
 */

@Data
public class CatalogQueryParam {

    @ApiModelProperty(value = "所属父栏目id")
    private Long parentId;

    @ApiModelProperty(value = "栏目编码")
    private String code;

    @ApiModelProperty(value = "栏目名称")
    private String catalogName;

    @ApiModelProperty(value = "栏目类型：0-内部栏目，1-外部栏目")
    private Integer type;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "是否前台可见")
    @TableField("is_visible")
    private Boolean visible;

    @ApiModelProperty(value = "是否允许投稿")
    private Boolean allowedSubmit;

}
