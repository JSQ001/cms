package com.eicas.cms.service;

import com.eicas.cms.entity.ArticleVisitLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.cms.pojo.param.DayArticleStaticsResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
* @author jsq
* @description 针对表【cms_article_visit_log】的数据库操作Service
* @createDate 2022-04-21 21:51:04
*/
public interface ArticleVisitLogService extends IService<ArticleVisitLog> {


    /**
     *
     * @param startTime
     * @param endTime
     * @return
     */
    Integer statisticLog(LocalDateTime startTime, LocalDateTime endTime);/**
     *
     * 统计时间范围内每天文章信息
     * @param startTime
     * @param endTime
     * @return
     */
    List<DayArticleStaticsResult> staticsArticleByDays(LocalDate startTime, LocalDate endTime);

}
