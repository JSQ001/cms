package com.eicas.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 对接统一认证单点登录服务器
 * 校验是否登录
 * 1、向认证服务发送请求获取令牌
 * 2、校验令牌（获取认证信息）
 * 3、创建本地会话（生成token）
 */

@Component
@Slf4j
public class IsLoginInterceptor implements HandlerInterceptor {

//    @Resource
//    private ClientAuthenticate clientAuthenticate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if(!StringUtils.hasText(clientAuthenticate.getAccessToken())){
//            log.info("重定向到授权码");
//            response.sendRedirect(clientAuthenticate.getRedirectUrl());
//            return false;
//        }
        return true;
    }

}
