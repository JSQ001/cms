package com.eicas.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.cms.entity.CatalogEntity;
import com.eicas.cms.pojo.param.CatalogArticleStaticsResult;
import com.eicas.cms.pojo.param.CatalogQueryParam;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 栏目信息表服务类
 *
 * @author osnudt
 * @since 2022-04-19
 */
public interface ICatalogService extends IService<CatalogEntity> {

    /**
     * 查询栏目信息
     * @param param 查询条件
     * @param current 当前分页
     * @param size 分页大小
     * @return 分页栏目数据
     */
    Page<CatalogEntity> listCatalog(CatalogQueryParam param, Integer current, Integer size);

    /**
     * 根据栏目ID查询其直接下级栏目
     * @param id 栏目ID
     * @param current 当前页
     * @param size 页面大小
     * @return 子栏目分页对象
     */
    Page<CatalogEntity> listChildCatalogById(Long id, Integer current, Integer size);

    /**
     * 根据父栏目id查询栏目树
     * @param parentId 父栏目ID
     * @return 子栏目分页对象
     */
    List<CatalogEntity> listCatalogTreeByParentId(Long parentId);

    /**
     * 移动栏目及下栏目下的信息至新父级栏目
     * @param id 被移动的栏目ID
     * @param parentId 目标栏目ID
     * @return 移动是否成功
     */
    Boolean moveCatalog(Long id, Long parentId);

    /**
     * 判断是否叶子栏目节点，仅叶子节点可以添加文章信息
     * @param id 栏目ID
     * @return 是否叶子节点
     */
    Boolean isLeafCatalog(Long id);

    /**
     * 查询指定ID栏目的直接下级栏目数量
     * @param id 栏目ID
     * @return 直接下级栏目数量
     */
    public Long countLeafCatalog(Long id);

    /**
     * 复制指定栏目为新栏目，新栏目对象
     * @param id 栏目ID
     * @return 新栏目对象
     */
    CatalogEntity copyCatalog(Long id);

    /**
     * 复制指定栏目为新栏目，新栏目对象
     * @param catalogEntity 栏目对象
     * @return 新栏目对象
     */
    boolean createCatalog(CatalogEntity catalogEntity);

    /**
     * 统计指定栏目子栏目时间段内文章信息
     * @param code 父栏目code
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 新栏目对象
     */
    List<CatalogArticleStaticsResult> staticsCatalogArticle(String code, LocalDateTime startTime, LocalDateTime endTime);
}
