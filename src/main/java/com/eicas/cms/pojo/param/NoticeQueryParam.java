package com.eicas.cms.pojo.param;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author osnudt
 * @since 2022/4/18
 */


@Data
public class NoticeQueryParam {
    /**
     * 通知公告标题
     */
    private String title;

    /**
     * 通知公告发布者
     */
    private String publisher;

    /**
     * 通知公告发布单位
     */
    private Long publishUnit;

    /**
     * 通知公告发布时间
     */
    private LocalDateTime beginPublishTime;

    /**
     * 通知公告发布时间
     */
    private LocalDateTime endPublishTime;

    /**
     * 通知公告状态：0-草稿,1-发布
     */
    private Integer state;

    /**
     * 是否显示：0-不显示，1-显示
     */
    private Boolean visible;
}
