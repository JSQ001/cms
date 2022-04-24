package com.eicas.cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 附件关联表
 *
 * @author osnudt
 * @since 2022-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cms_attachment_ref")
@ApiModel(value="AttachmentRef对象", description="附件关联表")
public class AttachmentRefEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    private Long attachmentId;

    @ApiModelProperty(value = "表标识：如article,notice,memorial")
    private String symbol;

    @ApiModelProperty(value = "表主键ID")
    private Long keyId;
}
