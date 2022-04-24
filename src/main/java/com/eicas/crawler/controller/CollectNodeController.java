package com.eicas.crawler.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.common.ResultData;
import com.eicas.crawler.webmagic.ArticleSpider;
import com.eicas.crawler.entity.CollectNodeEntity;
import com.eicas.crawler.service.ICollectNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 采集节点表 前端控制器
 *
 * @author osnudt
 * @since 2022-04-21
 */
@Slf4j
@RestController
@RequestMapping("/api/collect-node")
public class CollectNodeController {
    @Resource
    ICollectNodeService collectNodeService;
    @Resource
    private ArticleSpider spider;

    /**
     * 获取采集节点
     *
     * @param id 采集节点ID
     * @return 采集节点对象
     */
    @GetMapping(value = "/{id}")
    public ResultData<CollectNodeEntity> getCollectNodeById(@NotNull @PathVariable(value = "id") Long id) {
        CollectNodeEntity nodeEntity = collectNodeService.getById(id);
        if (nodeEntity == null) {
            return ResultData.failed("没有获取到采集节点");
        } else {
            return ResultData.success(nodeEntity, "成功获取采集节点");
        }
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
            @RequestParam(value = "current", defaultValue = "1") Integer current,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return collectNodeService.listAll(current, size);
    }

    /**
     * 保存新建采集节点
     *
     * @param entity 文章对象
     * @return 是否成功
     */
    @PostMapping(value = "/create")
    public ResultData<CollectNodeEntity> createCollectNode(@Valid @RequestBody CollectNodeEntity entity) {
        if (collectNodeService.saveCollectNode(entity) == null) {
            return ResultData.failed("新建采集节点失败");
        }
        return ResultData.success(entity,"新建采集节点成功");
    }

    /**
     * 删除采集节点
     * @param id 采集节点ID
     * @return 删除是否成功
     */
    @PostMapping("/delete/{id}")
    public ResultData deleteCollectNodeById(@PathVariable(value = "id") Long id) {
        if (!collectNodeService.deleteCollectNodeById(id)) {
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
        if (collectNodeService.saveCollectNode(entity) == null) {
            return ResultData.failed("更新采集节点失败");
        }
        return ResultData.success(entity,"更新采集节点成功");
    }

    /**
     * 手动采集
     */
    @PostMapping("/manual/{id}")
    public ResultData manualCollect(@PathVariable(value = "id") Long id) {

        spider.run(id);
        return ResultData.success("启动采集程序");
    }
}
