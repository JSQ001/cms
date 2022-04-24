package com.eicas.cms.pojo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ArticleAuditParam {

    @ApiModelProperty("文章id")
    @NotNull(message = "articleIds不能为空")
    private List<Long> articleIds;

    @ApiModelProperty("审核批注")
    private String auditComments;

    @ApiModelProperty("审核人")
    //@NotNull(message = "auditUserId不能为空")
    private Long auditUserId;

    @ApiModelProperty("审核时间")
    private LocalDateTime auditTime;

    @ApiModelProperty("审核状态：2：通过， 3：不通过")
    @NotNull(message = "state不能为空")
    private Integer state;

}
