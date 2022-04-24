package com.eicas.crawler.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.crawler.entity.CollectArticleEntity;
import com.eicas.crawler.mapper.CollectArticleMapper;
import com.eicas.crawler.service.ICollectArticleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 采集文章信息表 服务实现类
 *
 * @author osnudt
 * @since 2022-04-21
 */
@Service
public class CollectArticleServiceImpl extends ServiceImpl<CollectArticleMapper, CollectArticleEntity> implements ICollectArticleService {
    @Resource
    CollectArticleMapper collectArticleMapper;

    @Override
    public Page<CollectArticleEntity> listCollectArticle(Integer current, Integer size) {
        return collectArticleMapper.selectPage(Page.of(current, size), null);
    }

    @Override
    public Boolean hasRepetition(String originUrl) {
        return !collectArticleMapper.selectList(
                Wrappers.<CollectArticleEntity>lambdaQuery()
                        .eq(CollectArticleEntity::getOriginUrl, originUrl)).isEmpty();
    }
}
