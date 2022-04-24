package com.eicas.crawler.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.crawler.entity.CollectNodeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 采集节点表 服务类
 *
 * @author osnudt
 * @since 2022-04-21
 */
public interface ICollectNodeService extends IService<CollectNodeEntity> {

    CollectNodeEntity saveCollectNode(CollectNodeEntity entity);

    CollectNodeEntity updateCollectNode(CollectNodeEntity nodeEntity);

    Boolean deleteCollectNodeById(Long id);

    Page<CollectNodeEntity> listAll(Integer current, Integer size);
}
