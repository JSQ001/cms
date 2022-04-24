package com.eicas.cms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName cms_article_visit_log
 */
@TableName(value ="cms_article_visit_log")
@Data
public class ArticleVisitLog implements Serializable {
    /**
     * 文章访问记录表id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 文章id
     */
    @TableField(value = "article_id")
    private Long articleId;

    /**
     * 访问ip
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 访问时间
     */
    @TableField(value = "visit_time")
    private LocalDateTime visitTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}