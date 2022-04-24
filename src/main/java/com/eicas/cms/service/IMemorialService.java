package com.eicas.cms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.cms.entity.MemorialEntity;
import com.eicas.cms.pojo.param.MemorialQueryParam;

/**
 * <p>
 * 大事记表 服务类
 * </p>
 *
 * @author osnudt
 * @since 2022-04-18
 */
public interface IMemorialService extends IService<MemorialEntity> {

    /**
     * 查询大事记
     * 根据状态及事件发生年查询大事记
     */
    Page<MemorialEntity> listMemorial(MemorialQueryParam param, Integer current, Integer size);

}
