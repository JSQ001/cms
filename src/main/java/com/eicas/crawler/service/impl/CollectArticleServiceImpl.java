package com.eicas.crawler.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.param.ArticleStaticsByCollect;
import com.eicas.crawler.entity.CollectArticleEntity;
import com.eicas.crawler.mapper.CollectArticleMapper;
import com.eicas.crawler.service.ICollectArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 采集文章信息表 服务实现类
 * </p>
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
        return collectArticleMapper.selectPage(Page.of(current,size),null);
    }

    @Override
    public List<ArticleStaticsByCollect> statistic(LocalDateTime startTime, LocalDateTime endTime) {
        return collectArticleMapper.statistic(startTime, endTime);
    }
}
