package com.eicas.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.cms.entity.CatalogEntity;
import com.eicas.cms.mapper.CatalogMapper;
import com.eicas.cms.pojo.param.CatalogQueryParam;
import com.eicas.cms.service.IArticleService;
import com.eicas.cms.service.ICatalogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 栏目信息表 服务实现类
 *
 * @author osnudt
 * @since 2022-04-19
 */
@Service
public class CatalogServiceImpl extends ServiceImpl<CatalogMapper, CatalogEntity> implements ICatalogService {
    @Resource
    CatalogMapper catalogMapper;

    @Resource
    IArticleService articleService;

    @Override
    public Page<CatalogEntity> listCatalog(CatalogQueryParam param, Integer current, Integer size) {
        // TODO
        return null;
    }

    // TODO 只查找了直接下级栏目
    @Override
    public Page<CatalogEntity> listChildCatalogById(Long id, Integer current, Integer size) {
        return catalogMapper.selectPage(Page.of(current, size),
                Wrappers.<CatalogEntity>lambdaQuery().eq(CatalogEntity::getParentId, id));
    }

    @Override
    public Boolean moveCatalog(Long id, Long parentId) {
        LambdaUpdateWrapper<CatalogEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(CatalogEntity::getParentId, parentId);
        return this.update(wrapper);
    }

    @Override
    public Boolean isLeafCatalog(Long id) {
        return (this.countLeafCatalog(id) == 0L);
    }

    @Override
    public Long countLeafCatalog(Long id) {
        return catalogMapper.selectCount(
                Wrappers.<CatalogEntity>lambdaQuery().eq(CatalogEntity::getParentId, id));
    }

    @Override
    public CatalogEntity copyCatalog(Long id) {
        CatalogEntity catalogEntity = this.getById(id);
        catalogEntity.setId(null);
        catalogMapper.insert(catalogEntity);
        return catalogEntity;
    }
}
