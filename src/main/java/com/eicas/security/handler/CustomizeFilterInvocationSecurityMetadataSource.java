package com.eicas.security.handler;

import com.eicas.security.service.IAuthPermitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author osnudt
 * @since 2022/4/13
 * 安全元数据源
 */

@Component
public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    IAuthPermitService permitService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
//        //查询具体访问请求地址所需要的权限
//        List<AuthPermit> permitList = permitService.listPermitByReqPath(requestUrl);
//        if (permitList == null || permitList.size() == 0) {
//            //请求路径没有配置权限，表明该请求接口可以任意访问
//            return null;
//        }
//        String[] attributes = new String[permitList.size()];
//        for (int i = 0; i < permitList.size(); i++) {
//            attributes[i] = permitList.get(i).getCode();
//        }
//        return SecurityConfig.createList(attributes);
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
