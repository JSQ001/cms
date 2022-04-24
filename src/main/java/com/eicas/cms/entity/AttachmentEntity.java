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
 * <p>
 * 附件资源表
 * </p>
 *
 * @author osnudt
 * @since 2022-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cms_attachment")
@ApiModel(value="Attachment对象", description="附件资源表")
public class AttachmentEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "原始文件名")
    private String origin;

    @ApiModelProperty(value = "文件大小，以KB为单位")
    private Long size;

    @ApiModelProperty(value = "存储文件名")
    private String filename;

    @ApiModelProperty(value = "文件类型")
    private String ext;

    @ApiModelProperty(value = "mime类型")
    private String mime;

    @ApiModelProperty(value = "映射路径")
    private String mappingPath;

    @ApiModelProperty(value = "存储相对路径")
    private String relativePath;

    @ApiModelProperty(value = "存储绝对路径")
    private String absolutePath;

    @ApiModelProperty(value = "下载次数")
    private Integer hitNums;

    @ApiModelProperty(value = "上传人ID")
    private Long uploaderId;

    @ApiModelProperty(value = "上传人")
    private Long uploader;

    @ApiModelProperty(value = "上传IP")
    private String ipaddr;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean isDeleted;
}
