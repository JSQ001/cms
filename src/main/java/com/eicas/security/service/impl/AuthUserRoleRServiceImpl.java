package com.eicas.security.service.impl;

import com.eicas.security.entity.AuthUserRoleR;
import com.eicas.security.mapper.AuthUserRoleRMapper;
import com.eicas.security.service.IAuthUserRoleRService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author osnudt
 * @since 2022-04-24
 */
@Service
public class AuthUserRoleRServiceImpl extends ServiceImpl<AuthUserRoleRMapper, AuthUserRoleR> implements IAuthUserRoleRService {

}
