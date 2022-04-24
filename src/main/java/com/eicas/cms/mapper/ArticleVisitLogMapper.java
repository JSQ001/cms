package com.eicas.cms.mapper;

import com.eicas.cms.entity.ArticleVisitLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eicas.cms.pojo.param.ArticleStaticsResult;
import com.eicas.cms.pojo.param.DayArticleStaticsResult;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author jsq
* @description 针对表【cms_article_visit_log】的数据库操作Mapper
* @createDate 2022-04-21 21:51:04
* @Entity com.eicas.cms.entity.ArticleVisitLog
*/
public interface ArticleVisitLogMapper extends BaseMapper<ArticleVisitLog> {


    /**
     * 统计文章访问记录
     * */
    @Select("<script>" +
            "select count(a.id) " +
            "from  cms_article as a, cms_article_visit_log as l " +
            "<where>" +
            "   a.is_deleted = 0 " +
            "   and a.id = l.article_id" +
            "<when test='startTime != null'>" +
            "   and cms_article.publish_time &gt;= #{startTime}" +
            "</when>"+
            "<when test='endTime != null'>" +
            "   and cms_article.publish_time &lt;= #{endTime}" +
            "</when>"+
            "</where>" +
            "</script>"
    )
    Integer statisticLog(LocalDateTime startTime, LocalDateTime endTime);



    /**
    * 统计指定时间范围内每天文章信息
    * */
    @Select("<script>" +
            "<foreach collection='list' open='(' close=')' item='time' separator='union' >\n" +
            "   select t.day, count(a.id) as visitNum" +
            "   from (SELECT #{time} as 'day' from dual) t " +
            "   left join cms_article_visit_log as a" +
            "   on date(a.visit_time) = #{time}" +
            "   group by t.day" +
            "</foreach>\n"+
            "</script>")
    List<DayArticleStaticsResult> staticsArticleByDays(List list);
}




