package com.eicas.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.entity.CatalogEntity;
import com.eicas.cms.pojo.param.ArticleStaticsResult;
import com.eicas.cms.pojo.param.CatalogArticleStaticsResult;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 栏目信息表 Mapper 接口
 *
 * @author osnudt
 * @since 2022-04-19
 */
public interface CatalogMapper extends BaseMapper<CatalogEntity> {


    /**
     * 统计指定栏目的子栏目文章信息、访问信息
     * */
    @Select("<script>" +
            "select c.catalog_id as catalogId, c.catalog_name as catalogName,\n" +
            "count(l.id) as 'visitNum', \n" +
            "count(case when c.catalog_id is not null then 0 END) as 'allNum', \n" +
            "count(case when state = 0 then 0 end) as draftNum, " +
            "count(case when state = 1 then 0 end) as notAuditNum, \n" +
            "count(case when state = 2 then 0 end) as approvedNum,\n" +
            "count(case when state = 3 then 0 end) as rejectNum \n" +
            "from cms_article as a, cms_catalog as c\n" +
            "left join cms_article_visit_log as l" +
            "on a.id = l.article_id" +
            "<where>" +
            "   c.id = a.catalog_id " +
            "   a.is_deleted = 0 " +
            "   <when test='startTime != null'>" +
            "       and a.publish_time &gt;= #{startTime}" +
            "   </when>"+
            "   <when test='startTime != null'>" +
            "       and a.publish_time &lt;= #{endTime}" +
            "   </when>"+
            "   and c.id in (select id from cms_catalog  where parent_id = (select id from cms_catalog where code = #{code}))\n" +
            "</where>" +
            "group by c.catalog_id "+
            "</script>")
    List<CatalogArticleStaticsResult> staticsCatalogArticle(@Param("code") String code, LocalDateTime startTime, LocalDateTime endTime);


    /**
     * 根据id获取子级节点最大treeRel
     * */
    @Select("<script>" +
            "select ifnull((" +
            "   select Max(tree_rel) treeRel from cms_catalog " +
            "   <where>" +
            "       <choose>" +
            "           <when test='id != \"\" and id != null '>" +
            "               and parent_id = #{id}" +
            "           </when>" +
            "       <otherwise>" +
            "           and parent_id = 0" +
            "       </otherwise>" +
            "       </choose>" +
            "   </where>" +
            "),0) as treeRel\n" +
            "</script>")
    String getMaxTreeRelByParentId(Long id);

    /**
     * 查询栏目树
     * */
    @Select(
            "<script>" +
                    "select id, code, tree_rel, parent_id, catalog_name, cover_img_url, flag, content_template_url," +
                    "   is_allowed_submit, is_visible, remarks,type, path, publish_time, sort_order, keyword, " +
                    "   custom_url from cms_catalog" +
                    "<where>" +
                    "   and is_deleted = 0 " +
                    "   <choose>" +
                    "       <when test='id != \"\" and id != null '>" +
                    "           and parent_id = #{id}" +
                    "       </when>" +
                    "   <otherwise>" +
                    "       and parent_id = 0" +
                    "   </otherwise>" +
                    "   </choose>" +
                    "</where>" +
                    "order by sort_order, publish_time desc" +
                    "</script>"
    )
    @Results({
            @Result(column="id", property="id", id=true),
            @Result(property="children", column="id",  many=@Many(select="com.eicas.cms.mapper.CatalogMapper.listCatalogTreeByParentId", fetchType= FetchType.EAGER))
    })
    List<CatalogEntity> listCatalogTreeByParentId(Long id);
}
