package com.eicas.crawler.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.crawler.entity.CollectNodeEntity;
import com.eicas.crawler.mapper.CollectNodeMapper;
import com.eicas.crawler.scheduler.CronTaskRegistrar;
import com.eicas.crawler.scheduler.SchedulingRunnable;
import com.eicas.crawler.service.ICollectNodeService;
import com.eicas.utils.CommonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 采集节点表 服务实现类
 *
 * @author osnudt
 * @since 2022-04-21
 */
@Service
public class CollectNodeServiceImpl extends ServiceImpl<CollectNodeMapper, CollectNodeEntity> implements ICollectNodeService {
    @Resource
    CollectNodeMapper collectNodeMapper;

    @Resource
    CronTaskRegistrar cronTaskRegistrar;

    @Override
    public CollectNodeEntity saveCollectNode(CollectNodeEntity nodeEntity) {

        boolean result = this.saveOrUpdate(nodeEntity);
        CollectNodeEntity entity = this.getById(nodeEntity.getId());

        log.debug(entity.toString());
        /*
         * 定时器处理
         */
        if (result) {
            String  idStr = entity.getId().toString();
            String cron = CommonUtils.LocalDateTimeConvertToCron(entity.getAutoTime());
            SchedulingRunnable schedulingRunnable = new SchedulingRunnable(
                    "articleSpider",
                    "run",
                    idStr);
            if (entity.getAutomatic()) {
                cronTaskRegistrar.addCronTask(schedulingRunnable, cron);
            } else {
                cronTaskRegistrar.removeCronTask(schedulingRunnable);
            }
        }
        return entity;
    }

    @Override
    public CollectNodeEntity updateCollectNode(CollectNodeEntity nodeEntity) {

//        boolean result = this.updateById(nodeEntity);
//        log.debug(nodeEntity.toString());
//
//        return nodeEntity;
        return null;
    }

    @Override
    public Boolean deleteCollectNodeById(Long id) {
        SchedulingRunnable schedulingRunnable = new SchedulingRunnable("articleSpider",
                "run",
                id.toString());
        cronTaskRegistrar.removeCronTask(schedulingRunnable);
        return this.removeById(id);
    }

    @Override
    public Page<CollectNodeEntity> listAll(Integer current, Integer size) {
        return collectNodeMapper.selectPage(Page.of(current, size), null);
    }
}
