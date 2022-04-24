package com.eicas.crawler.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.param.CollectNodeParam;
import com.eicas.crawler.entity.CollectNodeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 采集节点表 Mapper 接口
 * </p>
 *
 * @author osnudt
 * @since 2022-04-21
 */
public interface CollectNodeMapper extends BaseMapper<CollectNodeEntity> {


    /**
     * 条件查询采集节点（分页）
     * */
    @Select("<script>" +
            "select n.*, c.catalog_name as catalogName from \n" +
            "cms_collect_node as n, cms_catalog as c\n" +
            "<where>" +
            "   n.is_deleted = 0 and c.is_deleted = 0 \t\n" +
            "   and c.id = n.catalog_id\t\n" +
            "<if test='param.name != null and param.name!=\"\"'>" +
            "   and name like CONCAT('%',#{param.name},'%')" +
            "</if> " +
            "<if test='param.catalogId != null'>" +
            "   and c.id in (" +
            "       select id from cms_catalog as c1, (select tree_rel from cms_catalog where id = #{param.catalogId}) t\n" +
            "       where c1.tree_rel LIKE concat(t.tree_rel,'%')" +
            "   )" +
            "</if> " +
            "</where>" +
            "</script>"
    )
    Page<CollectNodeEntity> listAll(CollectNodeParam param, Page<CollectNodeEntity> page);
}
