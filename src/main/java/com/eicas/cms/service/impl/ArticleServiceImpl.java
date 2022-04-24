package com.eicas.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.cms.entity.ArticleEntity;
import com.eicas.cms.entity.CatalogEntity;
import com.eicas.cms.mapper.ArticleMapper;
import com.eicas.cms.pojo.param.ArticleQueryParam;
import com.eicas.cms.pojo.vo.ArticleStatisticCompileVO;
import com.eicas.cms.pojo.vo.ArticleStatisticVisitVO;
import com.eicas.cms.service.IArticleService;
import com.eicas.cms.service.ICatalogService;
import com.eicas.common.ResultData;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 文章信息服务实现类
 *
 * @author osnudt
 * @since 2022-04-19
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity> implements IArticleService {
    @Resource
    ArticleMapper articleMapper;

    @Resource
    ICatalogService catalogService;

    @Override
    public ResultData<ArticleEntity> saveArticle(ArticleEntity entity) {
        Long catalogId = entity.getCatalogId();
        if (catalogId == null) {
            return ResultData.failed("没有指定文章所属栏目！");
        }

        CatalogEntity catalogEntity = catalogService.getById(catalogId);
        if (catalogEntity == null) {
            return ResultData.failed("指定文章所属栏目不存在！");
        }

        if (!catalogService.isLeafCatalog(catalogId)) {
            return ResultData.failed("指定文章所属栏目不存在不是叶子节点！");
        }

        entity.setCatalogName(catalogEntity.getCatalogName());
        entity.setCatalogEncode(catalogEntity.getCatalogName());
        this.save(entity);

        return ResultData.success(entity, "保存文章信息成功！");
    }

    @Override
    public ResultData<ArticleEntity> updateArticle(ArticleEntity entity) {
        this.updateById(entity);
        return ResultData.success(entity);
    }

    @Override
    public Page<ArticleEntity> listArticle(ArticleQueryParam param, Integer current, Integer size) {
        return articleMapper.selectPage(Page.of(current, size),
                Wrappers.<ArticleEntity>lambdaQuery()
                        .eq(param.getCatalogId() != null, ArticleEntity::getCatalogId, param.getCatalogId())
                        .ge(param.getBeginPublishTime() != null, ArticleEntity::getPublishTime, param.getBeginPublishTime())
                        .le(param.getEndPublishTime() != null, ArticleEntity::getPublishTime, param.getEndPublishTime())
                        .like(StringUtils.hasText(param.getTitle()), ArticleEntity::getTitle, param.getTitle())
                        .eq(param.getState() != null, ArticleEntity::getState, param.getState())
                        .eq(param.getVisible() != null, ArticleEntity::getVisible, param.getVisible())
                        .eq(param.getRecommended() != null, ArticleEntity::getRecommended, param.getRecommended())
                        .eq(param.getTop() != null, ArticleEntity::getTop, param.getTop())
                        .orderByDesc(ArticleEntity::getPublishTime));
    }

    @Override
    public Page<ArticleEntity> listArticleByCatalogId(Long catalogId, Integer current, Integer size) {
        return articleMapper.selectPage(Page.of(current, size),
                Wrappers.<ArticleEntity>lambdaQuery()
                        .eq(ArticleEntity::getCatalogId, catalogId));
    }

    @Override
    public ArticleStatisticCompileVO statisticArticleCompile(ArticleQueryParam param) {
        return null;
    }

    @Override
    public ArticleStatisticVisitVO statisticArticleVisit(ArticleQueryParam param) {
        return null;
    }

    @Override
    public Boolean moveArticle(Long id, Long catalogId) {
        CatalogEntity catalogEntity = catalogService.getById(catalogId);
        String catalogName = catalogEntity.getCatalogName();
        String catalogEnCode = catalogEntity.getEncode();

        LambdaUpdateWrapper<ArticleEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(ArticleEntity::getCatalogId, catalogId)
                .set(ArticleEntity::getCatalogName, catalogName)
                .set(ArticleEntity::getCatalogEncode, catalogEnCode);
        return update(wrapper);
    }

    @Override
    public Boolean hasRepetition(String originUrl) {
        return !articleMapper.selectList(
                Wrappers.<ArticleEntity>lambdaQuery()
                        .eq(ArticleEntity::getOriginUrl, originUrl)).isEmpty();
    }
}
