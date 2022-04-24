package com.eicas.cms.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.entity.ArticleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eicas.cms.pojo.param.ArticleQueryParam;
import com.eicas.cms.pojo.param.ArticleStaticsResult;
import com.eicas.cms.pojo.vo.ArticleBriefVO;
import org.apache.ibatis.annotations.Select;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文章信息表 Mapper 接口
 *
 * @author osnudt
 * @since 2022-04-19
 */

public interface ArticleMapper extends BaseMapper<ArticleEntity> {

    ArticleBriefVO selectBriefById(Serializable id);


    /**
     * 根据文章状态统计文章相关信息 0-草稿,1-待审核,2-审核通过,3-审核不通过
     * */
    @Select("<script>" +
            "select count(case when id is not null then 0 END) as allNum, " +
            "       count(case when state = 0 then 0 end) as draftNum, " +
            "       count(case when state = 1 then 0 end) as notAuditNum, " +
            "       count(case when state = 2 then 0 end) as approvedNum, " +
            "       count(case when state = 3 then 0 end) as rejectNum " +
            " from  cms_article " +
            "<where>" +
            "   is_deleted = 0 " +
            "<when test='startTime != null'>" +
            "   and cms_article.publish_time &gt;= #{startTime}" +
            "</when>"+
            "<when test='endTime != null'>" +
            "   and cms_article.publish_time &lt;= #{endTime}" +
            "</when>"+
            "</where>" +
            "</script>"
    )
    ArticleStaticsResult statisticArticleVisit(LocalDateTime startTime, LocalDateTime endTime);


    /**
     * 条件查询文章信息
     * @Param
     */
    @Select("<script>" +
            "select a.id, a.catalog_id, a.title, a.sub_title, a.keyword, a.essential, a.content, a.state, a.hit_nums,a.publish_time," +
            "a.is_visible as visible, a.is_focus as focus, a.is_recommended as recommended, a.is_top as top, a.sort_order, " +
            "a.cover_img_url, a.link_url, a.type, a.author, a.origin_url, a.source, a.remarks, c.catalog_name as catalogName from \n" +
            "cms_article as a, cms_catalog as c\n" +
            "<where>" +
            "   a.is_deleted = 0 and c.is_deleted = 0 \t\n" +
            "   and c.id = a.catalog_id\t\n" +
            "<if test='param.title != null and param.title!=\"\"'>" +
            "   and a.title like CONCAT('%',#{param.title},'%')" +
            "</if> " +
            "<if test='param.state != null'>" +
            "   and a.state = #{param.state}" +
            "</if> " +
            "<if test='param.visible != null'>" +
            "   and a.is_visible = #{param.visible}" +
            "</if> " +
            "<if test='param.focus != null'>" +
            "   and a.is_focus = #{param.focus}" +
            "</if> " +
            "<if test='param.recommended != null'>" +
            "   and a.is_recommended = #{param.recommended}" +
            "</if> " +
            "<if test='param.top != null'>" +
            "   and a.is_top = #{param.top}" +
            "</if> " +
            "<if test='param.beginPublishTime != null'>" +
            "   and a.publish_time &gt;= #{param.beginPublishTime}" +
            "</if> " +
            "<if test='param.endPublishTime != null'>" +
            "   and a.publish_time &lt;= #{param.endPublishTime}" +
            "</if> " +
            "<if test='param.catalogId != null'>" +
            "   and c.id in (" +
            "       select id from cms_catalog as c1, (select tree_rel from cms_catalog where id = #{param.catalogId}) t\n" +
            "       where c1.tree_rel LIKE concat(t.tree_rel,'%')" +
            "   )" +
            "</if> " +
            "</where>" +
            "ORDER BY a.sort_order, a.publish_time desc" +
            "</script>"
    )
    Page<ArticleEntity> listArticle(ArticleQueryParam param, Page<ArticleEntity> page);
}
