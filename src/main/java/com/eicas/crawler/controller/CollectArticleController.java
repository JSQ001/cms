package com.eicas.crawler.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.entity.ArticleEntity;
import com.eicas.cms.service.IArticleService;
import com.eicas.common.ResultData;
import com.eicas.crawler.entity.CollectArticleEntity;
import com.eicas.crawler.service.ICollectArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 采集文章信息表 前端控制器
 *
 * @author osnudt
 * @since 2022-04-21
 */
@RestController
@RequestMapping("/api/collect-article")
public class CollectArticleController {
    @Resource
    ICollectArticleService collectArticleService;
    @Resource
    IArticleService articleService;

    /**
     * 获取单个采集文章信息
     *
     * @param id 信息ID
     * @return 采集文章信息对象
     */
    @GetMapping(value = "/{id}")
    public CollectArticleEntity getCollectArticleById(@NotNull @PathVariable(value = "id") Long id) {
        return collectArticleService.getById(id);
    }

    /**
     * 获取采集文章信息分页数据
     *
     * @param current 当前分页
     * @param size    分页大小
     * @return 采集文章信息分页数据
     */
    @PostMapping(value = "/list")
    public Page<CollectArticleEntity> listCollectArticle(@RequestParam(value = "current", defaultValue = "1") Integer current,
                                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return collectArticleService.listCollectArticle(current, size);
    }

    /**
     * 删除采集文章信息
     *
     * @param id 采集文章信息ID
     * @return 删除是否成功
     */
    @PostMapping("/delete/{id}")
    public Boolean deleteCollectArticleById(@PathVariable(value = "id") Long id) {
        return collectArticleService.removeById(id);
    }

    /**
     * 批量删除采集文章信息
     *
     * @param ids 采集文章信息ID列表
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public Boolean batchDelete(@RequestBody List<Long> ids) {
        return collectArticleService.removeBatchByIds(ids);
    }

    /**
     * 更新采集文章信息
     *
     * @param entity 采集文章信息对象
     * @return 更新是否成功
     */
    @PostMapping("/update")
    public ResultData<CollectArticleEntity> updateCollectArticle(@Valid @RequestBody CollectArticleEntity entity) {
        if (!collectArticleService.updateById(entity)) {
            ResultData.failed("更新采集文章信息失败");
        }
        return ResultData.success(entity, "更新采集文章信息成功");
    }

    /**
     * 将采集到的单个数据入库到栏目
     *
     * @param id 采集文章信息ID
     * @return 入库后的文章信息对象
     */
    @PostMapping("/receive/{id}")
    public ResultData<ArticleEntity> receive(@PathVariable(value = "id") Long id) {
        // TODO
        CollectArticleEntity entity = collectArticleService.getById(id);
        ArticleEntity articleEntity = new ArticleEntity();
        BeanUtils.copyProperties(entity, articleEntity);

        articleService.save(articleEntity);
        return ResultData.success(articleEntity);
    }

    /**
     * 将采集到的数据批量入库到文章信息表
     *
     * @param ids 采集文章信息ID列表
     * @return 批量入库是否成功
     */
    @PostMapping("/receive")
    public ResultData<Boolean> batchReceive(@RequestBody List<Long> ids) {
        // TODO 使用批量插入方法
        for (Long id : ids) {
            CollectArticleEntity collectArticleEntity = collectArticleService.getById(id);
            ArticleEntity articleEntity = new ArticleEntity();
            BeanUtils.copyProperties(collectArticleEntity, articleEntity);
            articleService.saveArticle(articleEntity);
        }
        return ResultData.success(true);
    }
}
