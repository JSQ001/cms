package com.eicas.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.entity.CatalogEntity;
import com.eicas.cms.pojo.param.CatalogQueryParam;
import com.eicas.cms.service.ICatalogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 文章栏目前端控制
 *
 * @author osnudt
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Resource
    ICatalogService catalogService;

    /**
     * 根据ID查询单个栏目信息
     */
    @GetMapping(value = "/{id}")
    public CatalogEntity getCatalogById(@NotNull @PathVariable(value = "id") Long id) {
        return catalogService.getById(id);
    }

    /**
     * 根据栏目ID查询下属子栏目
     * @param id 栏目ID
     * @param current 当前页
     * @param size 页信息条数
     * @return 分页数据
     */
    @GetMapping(value = "/child")
    public Page<CatalogEntity> listChildCatalogById(@NotNull @RequestParam(value = "id") Long id,
                                                    @RequestParam(value = "current", defaultValue = "1") Integer current,
                                                    @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return catalogService.listChildCatalogById(id, current, size);
    }

    /**
     * 将指定栏目移动至目标栏目(parentId)下，成为其子栏目，顶级栏目ID为0
     * @param id 指定栏目ID
     * @param parentId 目标父栏目
     * @return 是否成功
     */
    @PostMapping(value = "/move")
    public Boolean moveCatalog(@NotNull @RequestParam(value = "id") Long id,
                               @NotNull @RequestParam(value = "parentId") Long parentId) {
        return catalogService.moveCatalog(id,parentId);
    }
    /**
     * 复制指定ID栏目为新栏目
     * @param id 指定栏目ID
     * @return 新栏目ID
     */
    @PostMapping(value = "/copy")
    public CatalogEntity copyCatalog(@NotNull @RequestParam(value = "id") Long id) {
        return catalogService.copyCatalog(id);
    }

    /**
     * 根据参数查询栏目，返回栏目分页数据
     */
    @PostMapping(value = "/list")
    public Page<CatalogEntity> listCatalog(@Valid @RequestBody CatalogQueryParam param,
                                           @RequestParam(value = "current", defaultValue = "1") Integer current,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return catalogService.listCatalog(param, current, size);
    }

    @PostMapping(value = "/create")
    public Boolean createCatalog(@Valid @RequestBody CatalogEntity entity) {
        return catalogService.save(entity);
    }

    @PostMapping("/delete/{id}")
    public Boolean deleteCatalogById(@PathVariable(value = "id") Long id) {
        return catalogService.removeById(id);
    }

    @PostMapping("/delete")
    public Boolean batchDelete(@RequestBody List<Long> ids) {
        return catalogService.removeBatchByIds(ids);
    }

    @PostMapping("/update")
    public Boolean updateCatalog(@Valid @RequestBody CatalogEntity entity) {
        return catalogService.updateById(entity);
    }

}
