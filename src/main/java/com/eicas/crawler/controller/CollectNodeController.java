package com.eicas.crawler.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.param.CollectNodeParam;
import com.eicas.common.ResultData;
import com.eicas.crawler.ArticleSpider;
import com.eicas.crawler.entity.CollectNodeEntity;
import com.eicas.crawler.service.ICollectNodeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 采集节点表 前端控制器
 * </p>
 *
 * @author osnudt
 * @since 2022-04-21
 */
@RestController
@RequestMapping("/api/collect-node")
public class CollectNodeController {

    @Resource
    ICollectNodeService collectNodeService;
    @Resource
    ArticleSpider articleSpider;
    /**
     * 获取采集节点
     *
     * @param id 采集节点ID
     * @return 采集节点对象
     */
    @GetMapping(value = "/{id}")
    public CollectNodeEntity getCollectNodeById(@NotNull @PathVariable(value = "id") Long id) {
        return collectNodeService.getById(id);
    }

    /**
     * 获取所有采集节点
     *
     * @param current 当前分页
     * @param size    分页大小
     * @return 采集节点分页数据
     */
    @PostMapping(value = "/list")
    public Page<CollectNodeEntity> listCollectNode(
            CollectNodeParam param,
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return collectNodeService.listAll(param, current, size);
    }


    /**
     * 保存新建采集节点
     *
     * @param entity 文章对象
     * @return 是否成功
     */
    @PostMapping(value = "/create")
    public ResultData<CollectNodeEntity> createCollectNode(@Valid @RequestBody CollectNodeEntity entity) {
        if (collectNodeService.save(entity)) {
            return ResultData.success(entity);
        }
        return ResultData.failed("保存新建采集节点失败");
    }

    /**
     * 删除采集节点
     *
     * @param id 采集节点ID
     * @return 删除是否成功
     */
    @PostMapping("/delete/{id}")
    public ResultData deleteCollectNodeById(@PathVariable(value = "id") Long id) {
        if (!collectNodeService.removeById(id)) {
            return ResultData.failed("删除采集节点失败");
        }
        return ResultData.success("删除采集节点成功");

    }

    /**
     * 批量删除采集节点
     *
     * @param ids 采集节点ID列表
     * @return 删除是否成功
     */
    @PostMapping("/delete")
    public ResultData batchDelete(@RequestBody List<Long> ids) {
        if (!collectNodeService.removeBatchByIds(ids)) {
            return ResultData.failed("批量删除采集节点失败");
        }
        return ResultData.success("批量删除采集节点成功");
    }

    /**
     * 更新指定ID的采集节点
     *
     * @param entity 采集节点对象
     * @return 更新是否成功
     */
    @PostMapping("/update")
    public ResultData<CollectNodeEntity> updateCollectNode(@Valid @RequestBody CollectNodeEntity entity) {
        if (!collectNodeService.updateById(entity)) {
            return ResultData.failed("更新采集节点信息失败");
        }
        return ResultData.success(entity);
    }

    /**
     * 手动采集
     *
     * @param id 采集规则id
     *
     */
    @PostMapping("/manual/{id}")
    public ResultData<Boolean> manualCollect(@PathVariable(value = "id")Long id) {
        CollectNodeEntity nodeEntity = collectNodeService.getById(id);
        articleSpider.run(nodeEntity);
        return ResultData.success(true);
    }
}
