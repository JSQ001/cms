package com.eicas.cms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.cms.entity.MemorialEntity;
import com.eicas.cms.mapper.MemorialMapper;
import com.eicas.cms.pojo.param.MemorialQueryParam;
import com.eicas.cms.service.IMemorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 大事记服务实现
 *
 * @author osnudt
 * @since 2022-04-18
 */
@Service
public class MemorialServiceImpl extends ServiceImpl<MemorialMapper, MemorialEntity> implements IMemorialService {

    @Resource
    MemorialMapper memorialMapper;

    @Override
    public Page<MemorialEntity> listMemorial(MemorialQueryParam param, Integer current, Integer size) {
        return memorialMapper.selectPage(Page.of(current, size),
                Wrappers.<MemorialEntity>lambdaQuery()
                        .ge(param.getBeginEventTime() != null,MemorialEntity::getEventTime, param.getBeginEventTime())
                        .le(param.getEndEventTime() != null,MemorialEntity::getEventTime, param.getEndEventTime())
                        .ge(param.getBeginPublishTime() != null, MemorialEntity::getPublishTime, param.getBeginPublishTime())
                        .ge(param.getEndPublishTime() != null, MemorialEntity::getPublishTime, param.getEndPublishTime())
                        .like(StringUtils.hasText(param.getTitle()), MemorialEntity::getTitle,param.getTitle())
                        .eq(param.getState() != null, MemorialEntity::getState, param.getState())
                        .orderByDesc(MemorialEntity::getEventTime));
    }
}
