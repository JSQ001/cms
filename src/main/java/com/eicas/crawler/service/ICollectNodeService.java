package com.eicas.crawler.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.pojo.param.CollectNodeParam;
import com.eicas.common.ResultData;
import com.eicas.crawler.entity.CollectNodeEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 采集节点表 服务类
 * </p>
 *
 * @author osnudt
 * @since 2022-04-21
 */
public interface ICollectNodeService extends IService<CollectNodeEntity> {

    ResultData<CollectNodeEntity> saveCollectNode(CollectNodeEntity entity);

    Page<CollectNodeEntity> listAll(CollectNodeParam collectNodeParam, Integer current, Integer size);
}
