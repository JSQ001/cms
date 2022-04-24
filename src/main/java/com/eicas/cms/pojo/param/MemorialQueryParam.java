package com.eicas.cms.pojo.param;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author osnudt
 * @since 2022/4/18
 */


@Data
public class MemorialQueryParam {
    /**
     * 事件时间-起始
     */
    private LocalDateTime beginEventTime;
    /**
     * 事件时间-截止
     */
    private LocalDateTime endEventTime;
    /**
     * 发布时间-起始
     */
    private LocalDateTime beginPublishTime;
    /**
     * 发布时间-截止
     */
    private LocalDateTime endPublishTime;
    /**
     * 状态：0-草稿,1-已发布
     */
    private Integer state;
    /**
     * 通知公告标题（模糊匹配）
     */
    private String title;
}
