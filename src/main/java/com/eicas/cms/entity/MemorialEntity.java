package com.eicas.cms.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 大事记实体类
 *
 * @author osnudt
 * @since 2022-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cms_memorial")
public class MemorialEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 摘要
     */
    private String essential;

    /**
     * 标题图片
     */
    private String coverImgUrl;

    /**
     * 编制人
     */
    private String compiler;

    /**
     * 编制人用户编号
     */
    private Long compilerId;

    /**
     * 编制单位
     */
    private String compilerUnit;

    /**
     * 描述
     */
    private String remarks;

    /**
     * 状态：0-草稿,1-已发布
     */
    private Integer state;

    /**
     * 事件时间
     */
    @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventTime;

    /**
     * 发布时间
     */
    @JsonFormat(locale = "zh",timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishTime;

    /**
     * 自定义顺序
     */
    private Integer sortOrder;

    /**
     * 点击次数
     */
    private Integer hitNums;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField("is_deleted")
    private Boolean deleted;
}
