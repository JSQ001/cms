package com.eicas.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eicas.security.entity.AuthPermit;
import com.eicas.security.entity.AuthRole;
import com.eicas.security.entity.AuthUser;
import com.eicas.security.entity.AuthUserRoleR;
import com.eicas.security.mapper.AuthUserMapper;
import com.eicas.security.mapper.AuthUserRoleRMapper;
import com.eicas.security.pojo.param.UserPasswordParam;
import com.eicas.security.pojo.param.UserRegisterParam;
import com.eicas.security.service.IAuthUserRoleRService;
import com.eicas.security.service.IAuthUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统账号表 服务实现类
 *
 * @author osnudt
 * @since 2022-04-23
 */
@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, AuthUser> implements IAuthUserService {
    @Resource
    AuthUserMapper authUserMapper;
    @Resource
    AuthUserRoleRMapper authUserRoleRMapper;
    @Resource
    IAuthUserRoleRService authUserRoleRService;
    @Resource
    private PasswordEncoder passwordEncoder;


    /**
     * 根据用户账号名称获取用户权限信息，构造UserDetails对象
     *
     * @param username 用户账号名称
     * @return User对象（包含用户名、密码、权限集合）
     * @throws UsernameNotFoundException 用户不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = this.getOne(Wrappers.<AuthUser>lambdaQuery().eq(AuthUser::getUsername, username), false);
        if (authUser == null) {
            throw new RuntimeException("用户不存在");
        }
        List<AuthPermit> permits = authUserMapper.selectPermitListByUserId(authUser.getId());
//        permits = permits.stream().distinct().collect(Collectors.toList());
        List<GrantedAuthority> authorities = new ArrayList<>();
        permits.forEach(permit -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(permit.getPermitKey());
            authorities.add(authority);
        });
        return new User(authUser.getUsername(), authUser.getPassword(), authorities);
    }

    @Override
    public AuthUser register(UserRegisterParam param) {
        AuthUser user = new AuthUser();
        BeanUtils.copyProperties(param, user);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNotExpired(true);

        List<AuthUser> userList = authUserMapper.selectList(Wrappers.<AuthUser>lambdaQuery().eq(AuthUser::getUsername, param.getUsername()));
        if (userList.size() > 0) {
            return null;
        }
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        authUserMapper.insert(user);
        return user;
    }

    @Override
    public AuthUser getAuthUserByUsername(String username) {
        List<AuthUser> userList = authUserMapper.selectList(Wrappers.<AuthUser>lambdaQuery().eq(AuthUser::getUsername, username));
        return userList.get(0);
    }

    @Override
    public AuthUser getUserDetailsByUsername(String username) {
        return null;
    }

    @Override
    public boolean login(String username, String password) {
        boolean result = false;
        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                log.error("用户名密码不匹配");
                result = false;
            }
            if (!userDetails.isEnabled()) {
                log.error("账号被禁用");
                result = false;
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null,
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            result = true;
        } catch (Exception e) {
            log.error("登录异常");
        }
        return result;
    }

    @Override
    public boolean updateRole(Long userId, List<Long> roleIds) {
        //先删除原来的关系
        authUserRoleRService.remove(Wrappers.<AuthUserRoleR>lambdaQuery().eq(AuthUserRoleR::getUserId,userId));
        //建立新关系
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<AuthUserRoleR> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                AuthUserRoleR roleRelation = new AuthUserRoleR();
                roleRelation.setUserId(userId);
                roleRelation.setRoleId(roleId);
                list.add(roleRelation);
            }
            authUserRoleRService.saveBatch(list);
        }
        return true;
    }

    @Override
    public List<AuthRole> listRolesById(Long userId) {
        return authUserMapper.selectRoleListByUserId(userId);
    }

    @Override
    public List<AuthPermit> listPermitsById(Long userId) {
        return authUserMapper.selectPermitListByUserId(userId);
    }

    @Override
    public int updatePassword(UserPasswordParam param) {
        if (StrUtil.isEmpty(param.getUsername())
                || StrUtil.isEmpty(param.getOldPassword())
                || StrUtil.isEmpty(param.getNewPassword())) {
            return -1;
        }
        List<AuthUser> userList = authUserMapper.selectList(Wrappers.<AuthUser>lambdaQuery().eq(AuthUser::getUsername, param.getUsername()));
        if (userList.size() > 0) {
            return -2;
        }
        AuthUser user = userList.get(0);
        if (!passwordEncoder.matches(param.getOldPassword(), user.getPassword())) {
            return -3;
        }
        user.setPassword(passwordEncoder.encode(param.getNewPassword()));
        authUserMapper.updateById(user);
        return 1;

    }
}
