package com.eicas.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.entity.ArticleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.cms.pojo.param.ArticleQueryParam;
import com.eicas.cms.pojo.vo.ArticleStatisticCompileVO;
import com.eicas.cms.pojo.vo.ArticleStatisticVisitVO;
import com.eicas.common.ResultData;

/**
 *
 * 文章信息表 服务类
 *
 * @author osnudt
 * @since 2022-04-19
 */
public interface IArticleService extends IService<ArticleEntity> {
    /**
     * 保存文章对象，校验并更新文章所属栏目信息
     *
     * @param entity 文章对象
     * @return 文章对象保存结果
     */
    ResultData<ArticleEntity> saveArticle(ArticleEntity entity);

    /**
     * 更新文章对象，校验并更新文章所属栏目信息
     *
     * @param entity 文章对象
     * @return 文章对象保存结果
     */
    ResultData<ArticleEntity> updateArticle(ArticleEntity entity);

    /**
     *
     * @param param
     * @param current
     * @param size
     * @return
     */
    Page<ArticleEntity> listArticle(ArticleQueryParam param, Integer current, Integer size);

    /**
     *
     * @param catalogId
     * @param current
     * @param size
     * @return
     */
    Page<ArticleEntity> listArticleByCatalogId(Long catalogId, Integer current, Integer size);

    /**
     *
     * @param param
     * @return
     */
    ArticleStatisticCompileVO statisticArticleCompile(ArticleQueryParam param);

    /**
     *
     * @param param
     * @return
     */
    ArticleStatisticVisitVO statisticArticleVisit(ArticleQueryParam param);

    /**
     *
     * @param id
     * @param catalogId
     * @return
     */
    Boolean moveArticle(Long id, Long catalogId);

    /**
     * 查询是否存在原文网址为originUrl的文章信息
     * @param originUrl 文章原文网址
     * @return 有返回真，无则返回假
     */
    Boolean hasRepetition(String originUrl);
}
