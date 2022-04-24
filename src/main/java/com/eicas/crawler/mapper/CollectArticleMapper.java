package com.eicas.crawler.mapper;

import com.eicas.cms.pojo.param.ArticleStaticsByCollect;
import com.eicas.cms.pojo.param.ArticleStaticsResult;
import com.eicas.crawler.entity.CollectArticleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 采集文章信息表 Mapper 接口
 * </p>
 *
 * @author osnudt
 * @since 2022-04-21
 */
public interface CollectArticleMapper extends BaseMapper<CollectArticleEntity> {


    /**
     * 根据文章状态统计文章相关信息 0-草稿,1-待审核,2-审核通过,3-审核不通过
     * */
    @Select("<script>" +
            "select l.catalog_name as catalogName" +
            "       count(case when id is not null then 0 END) as allNum, " +
            "       count(case when state = 0 then 0 end) as draftNum, " +
            "       count(case when state = 1 then 0 end) as notAuditNum, " +
            "       count(case when state = 2 then 0 end) as approvedNum, " +
            "       count(case when state = 3 then 0 end) as rejectNum " +
            " from  cms_article as a, cms_catalog as l" +
            "<where>" +
            "   a.is_deleted = 0 and l.is_deleted = 0 and a.catalog_id = l.id" +
            "<when test='startTime != null'>" +
            "   and cms_article.publish_time &gt;= #{startTime}" +
            "</when>"+
            "<when test='endTime != null'>" +
            "   and cms_article.publish_time &lt;= #{endTime}" +
            "</when>"+
            "</where>" +
            "group by catalogName" +
            "</script>"
    )
    List<ArticleStaticsByCollect> statistic(LocalDateTime startTime, LocalDateTime endTime);

}
