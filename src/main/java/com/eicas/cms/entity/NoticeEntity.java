package com.eicas.cms.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 通知公告实体类
 *
 * @author osnudt
 * @since 2022-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cms_notice")
public class NoticeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文章标题
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
     * 发布者用户编号
     */
    private Long publisherId;

    /**
     * 发布者
     */
    private String publisher;

    /**
     * 发布单位
     */
    private Long publishUnit;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 附件
     */
    private Long attachmentId;

    /**
     * 描述
     */
    private String remarks;

    /**
     * 文章状态：0-草稿,1-发布
     */
    private Integer state;

    /**
     * 是否显示：0-不显示，1-显示
     */
    private Boolean visible;

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
    private Boolean deleted;
}
