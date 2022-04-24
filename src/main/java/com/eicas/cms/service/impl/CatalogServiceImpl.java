package com.eicas.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.cms.entity.CatalogEntity;
import com.eicas.cms.mapper.CatalogMapper;
import com.eicas.cms.pojo.param.CatalogArticleStaticsResult;
import com.eicas.cms.pojo.param.CatalogQueryParam;
import com.eicas.cms.service.IArticleService;
import com.eicas.cms.service.ICatalogService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 栏目信息表 服务实现类
 * </p>
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
        LambdaQueryWrapper<CatalogEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(param.getParentId() != null, CatalogEntity::getParentId, param.getParentId());
        wrapper.likeLeft(StringUtils.hasText(param.getCode()), CatalogEntity::getCode, param.getCode());
        wrapper.like(StringUtils.hasText(param.getCatalogName()), CatalogEntity::getCatalogName, param.getCatalogName());
        wrapper.eq(param.getType() != null, CatalogEntity::getType, param.getType());
        wrapper.like(StringUtils.hasText(param.getKeyword()), CatalogEntity::getKeyword, param.getKeyword());
        wrapper.eq(param.getVisible() != null, CatalogEntity::getVisible, param.getVisible());
        wrapper.eq(param.getAllowedSubmit() != null, CatalogEntity::getAllowedSubmit, param.getAllowedSubmit());
        wrapper.orderBy(true, true, CatalogEntity::getAllowedSubmit);
        return page(Page.of(current,size),wrapper);
    }

    // TODO 只查找了直接下级栏目
    @Override
    public Page<CatalogEntity> listChildCatalogById(Long id, Integer current, Integer size) {
        return catalogMapper.selectPage(Page.of(current, size),
                Wrappers.<CatalogEntity>lambdaQuery().eq(CatalogEntity::getParentId, id));
    }

    @Override
    public List<CatalogEntity> listCatalogTreeByParentId(Long parentId) {
        return catalogMapper.listCatalogTreeByParentId(parentId);
    }

    @Override
    public Boolean moveCatalog(Long id, Long parentId) {
        CatalogEntity entity = new CatalogEntity();
        entity.setTreeRel(getTreeRel(parentId));
        entity.setParentId(parentId);
        entity.setId(id);
        return this.updateById(entity);
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

    @Override
    public boolean createCatalog(CatalogEntity catalogEntity) {
        catalogEntity.setTreeRel(getTreeRel(catalogEntity.getParentId()));
        return this.save(catalogEntity);
    }

    private String getTreeRel(Long parentId){
        String maxEncode = catalogMapper.getMaxTreeRelByParentId(parentId);
        if(parentId == null || parentId == 0){
            return String.valueOf(Integer.parseInt(maxEncode) + 1);
        }else {
            String[] codeList = maxEncode.split("-");
            String lastCode = codeList[codeList.length-1];
            if(lastCode.equals("0")){
                CatalogEntity parent = getById(parentId);
                return parent.getTreeRel() + "-" + 1;
            }else {
                codeList[codeList.length-1] = String.valueOf(Integer.parseInt(codeList[codeList.length-1]) + 1);
                return String.join("-", codeList);
            }
        }
    }

    @Override
    public List<CatalogArticleStaticsResult> staticsCatalogArticle(String code, LocalDateTime startTime, LocalDateTime endTime) {
        return catalogMapper.staticsCatalogArticle(code,startTime,endTime);
    }
}
