package com.eicas.cms.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CollectNodeParam {
    @ApiModelProperty(value = "规则名称")
    private String name;
    @ApiModelProperty(value = "所属栏目ID")
    private Long CatalogId;
}
