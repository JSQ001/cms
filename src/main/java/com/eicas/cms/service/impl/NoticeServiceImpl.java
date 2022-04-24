package com.eicas.cms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.cms.entity.NoticeEntity;
import com.eicas.cms.mapper.NoticeMapper;
import com.eicas.cms.pojo.param.NoticeQueryParam;
import com.eicas.cms.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通知公告表 服务实现类
 *
 * @author osnudt
 * @since 2022-04-18
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, NoticeEntity> implements INoticeService {
    @Autowired
    NoticeMapper noticeMapper;

    @Override
    public Page<NoticeEntity> listNotice(NoticeQueryParam param, Integer current, Integer size) {
        return noticeMapper.selectPage(Page.of(current, size),
                Wrappers.<NoticeEntity>lambdaQuery()
                        .between(NoticeEntity::getPublishTime, param.getBeginPublishTime(), param.getEndPublishTime())
                        .eq(NoticeEntity::getPublisher, param.getPublisher())
                        .like(NoticeEntity::getTitle, param.getTitle())
                        .eq(NoticeEntity::getPublishUnit, param.getPublishUnit())
                        .eq(NoticeEntity::getState, param.getState())
                        .orderByDesc(NoticeEntity::getPublishTime));
    }
}
