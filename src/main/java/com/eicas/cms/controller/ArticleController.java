package com.eicas.cms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.entity.ArticleEntity;
import com.eicas.cms.pojo.param.ArticleQueryParam;
import com.eicas.cms.pojo.vo.ArticleStatisticCompileVO;
import com.eicas.cms.pojo.vo.ArticleStatisticVisitVO;
import com.eicas.cms.service.IArticleService;
import com.eicas.common.ResultData;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 文章信息表 前端控制器
 *
 * @author osnudt
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    IArticleService articleService;

    /**
     * 获取单个文章信息
     *
     * @param id 信息ID
     * @return 文章信息对象
     */
    @GetMapping(value = "/{id}")
    public ArticleEntity getArticleById(@NotNull @PathVariable(value = "id") Long id) {
        return articleService.getById(id);
    }

    /**
     * 根据参数查询文章信息
     *
     * @param param   查询参数
     * @param current 当前分页
     * @param size    分页大小
     * @return 文章信息分页数据
     */
    @PostMapping(value = "/list")
    public Page<ArticleEntity> listArticle(@Valid @RequestBody ArticleQueryParam param,
                                           @RequestParam(value = "current", defaultValue = "1") Integer current,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return articleService.listArticle(param, current, size);
    }

    /**
     * 根据指定栏目ID查询文章信息列表
     *
     * @param catalogId 栏目ID
     * @param current   当前页码
     * @param size      页面大小
     * @return 文章信息分页数据
     */
    @PostMapping(value = "/list/catalog")
    public Page<ArticleEntity> listArticleByCatalogId(@NotNull @RequestParam(value = "catalogId") Long catalogId,
                                                      @RequestParam(value = "current", defaultValue = "1") Integer current,
                                                      @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return articleService.listArticleByCatalogId(catalogId, current, size);
    }

    /**
     * 保存新建文章，必须指定已存在的栏目
     *
     * @param entity 文章对象
     * @return 是否成功
     */
    @PostMapping(value = "/create")
    public ResultData<ArticleEntity> createArticle(@Valid @RequestBody ArticleEntity entity) {
        return articleService.saveArticle(entity);
    }

    /**
     * 删除文章信息
     *
     * @param id 文章对象ID
     * @return 删除是否成功
     */
    @PostMapping("/delete/{id}")
    public Boolean deleteArticleById(@PathVariable(value = "id") Long id) {
        return articleService.removeById(id);
    }

    /**
     * 批量删除文章信息
     *
     * @param ids 文章对象ID列表
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Boolean batchDelete(@RequestBody List<Long> ids) {
        return articleService.removeBatchByIds(ids);
    }

    /**
     * 更新指定ID的文章信息
     *
     * @param entity 文章信息对象
     * @return 更新是否成功
     */
    @PostMapping("/update")
    public ResultData<ArticleEntity> updateArticle(@Valid @RequestBody ArticleEntity entity) {
        return articleService.updateArticle(entity);
    }

    /**
     * 将指定ID的文章信息移动至另一个栏目
     *
     * @param id        文章信息ID
     * @param catalogId 栏目ID
     * @return 移动是否成功
     */
    @PostMapping(value = "/move")
    public Boolean moveArticle(@NotNull @RequestParam(value = "id") Long id,
                               @RequestParam(value = "catalogId") Long catalogId) {
        return articleService.moveArticle(id, catalogId);
    }


    /**
     * TODO 根据条件统计文章访问计数情况
     *
     * @param param 查询条件
     * @return 统计数据对象
     */
    @PostMapping(value = "/statistic/visit")
    public ArticleStatisticVisitVO statisticArticleVisit(@Valid @RequestBody ArticleQueryParam param) {
        return articleService.statisticArticleVisit(param);
    }

    /**
     * TODO 根据条件统计文章采编情况
     *
     * @param param 查询条件
     * @return 统计数据对象
     */
    @PostMapping(value = "/statistic/compile")
    public ArticleStatisticCompileVO statisticArticleCompile(@Valid @RequestBody ArticleQueryParam param) {
        return articleService.statisticArticleCompile(param);
    }

    /**
     * TODO
     *
     * @param entity
     * @return
     */
    @PostMapping("/hits")
    public Boolean updateArticleHitNums(@Valid @RequestBody ArticleEntity entity) {
        return false;
    }

    /**
     * TODO
     *
     * @param entity
     * @return
     */
    @GetMapping("/hits")
    public Boolean getArticleHitNums(@Valid @RequestBody ArticleEntity entity) {
        return false;
    }
}
