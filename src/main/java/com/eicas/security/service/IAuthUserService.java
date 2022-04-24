package com.eicas.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eicas.security.entity.AuthPermit;
import com.eicas.security.entity.AuthRole;
import com.eicas.security.entity.AuthUser;
import com.eicas.security.pojo.param.UserPasswordParam;
import com.eicas.security.pojo.param.UserRegisterParam;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统账号表 服务类
 *
 * @author osnudt
 * @since 2022-04-23
 */
public interface IAuthUserService extends IService<AuthUser>, UserDetailsService {

    /**
     * 注册功能
     */
    AuthUser register(UserRegisterParam param);

    /**
     * 根据用户账号名名获取用户对象
     * @param username 用户账号名
     * @return 用户对象
     */
    AuthUser getAuthUserByUsername(String username);

    /**
     * 根据用户账号名名获取用户详情信息对象
     * @param username 用户账号名
     * @return 用户详情信息对象
     */
    AuthUser getUserDetailsByUsername(String username);
    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    boolean login(String username, String password);

    /**
     * 修改用户角色关系
     * @return
     */
    @Transactional
    boolean updateRole(Long userId, List<Long> roleIds);

    /**
     * 获取用户对应角色
     */
    List<AuthRole> listRolesById(Long userId);

    /**
     * 获取指定用户的可访问资源
     */
    List<AuthPermit> listPermitsById(Long userId);

    /**
     * 更新用户密码，返回状态码
     */
    int updatePassword(UserPasswordParam param);
}
