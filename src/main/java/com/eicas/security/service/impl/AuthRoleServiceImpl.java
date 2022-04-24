package com.eicas.security.service.impl;

import com.eicas.security.entity.AuthRole;
import com.eicas.security.mapper.AuthRoleMapper;
import com.eicas.security.service.IAuthRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 角色信息表 服务实现类
 *
 * @author osnudt
 * @since 2022-04-23
 */
@Service
public class AuthRoleServiceImpl extends ServiceImpl<AuthRoleMapper, AuthRole> implements IAuthRoleService {

}
