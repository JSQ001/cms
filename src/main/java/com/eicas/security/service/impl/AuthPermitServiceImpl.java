package com.eicas.security.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.security.entity.AuthPermit;
import com.eicas.security.mapper.AuthPermitMapper;
import com.eicas.security.pojo.param.PermitQueryParam;
import com.eicas.security.service.IAuthPermitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 权限信息表 服务实现类
 *
 * @author osnudt
 * @since 2022-04-23
 */
@Service
public class AuthPermitServiceImpl extends ServiceImpl<AuthPermitMapper, AuthPermit> implements IAuthPermitService {
    @Resource
    AuthPermitMapper authPermitMapper;

    @Override
    public Page<AuthPermit> listPermits(PermitQueryParam param, Integer current, Integer size) {
        // TODO
        return authPermitMapper.selectPage(Page.of(current, size),
                Wrappers.<AuthPermit>lambdaQuery()
                        .orderByDesc(AuthPermit::getSortOrder));
    }

    @Override
    public Page<AuthPermit> listAll() {
        // TODO
        return null;
    }
}
