package com.eicas.crawler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eicas.cms.entity.CatalogEntity;
import com.eicas.cms.pojo.param.CollectNodeParam;
import com.eicas.cms.service.ICatalogService;
import com.eicas.common.ResultData;
import com.eicas.crawler.entity.CollectNodeEntity;
import com.eicas.crawler.mapper.CollectNodeMapper;
import com.eicas.crawler.service.ICollectNodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 采集节点表 服务实现类
 * </p>
 *
 * @author osnudt
 * @since 2022-04-21
 */
@Service
public class CollectNodeServiceImpl extends ServiceImpl<CollectNodeMapper, CollectNodeEntity> implements ICollectNodeService {
    @Resource
    CollectNodeMapper collectNodeMapper;

    @Override
    public ResultData<CollectNodeEntity> saveCollectNode(CollectNodeEntity entity) {
        return null;
    }

    @Override
    public Page<CollectNodeEntity> listAll(CollectNodeParam param, Integer current, Integer size) {

        return collectNodeMapper.listAll(param, Page.of(current,size));
    }
}
