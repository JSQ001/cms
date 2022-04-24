package com.eicas.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.cms.entity.NoticeEntity;
import com.eicas.cms.pojo.param.NoticeQueryParam;

/**
 * <p>
 * 通知公告服务接口
 * </p>
 *
 * @author osnudt
 * @since 2022-04-18
 */
public interface INoticeService extends IService<NoticeEntity> {
    /**
     * 查询通知公告
     */
    Page<NoticeEntity> listNotice(NoticeQueryParam param, Integer current, Integer size);
}
