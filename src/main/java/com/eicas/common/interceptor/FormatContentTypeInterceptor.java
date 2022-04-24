package com.eicas.common.interceptor;

import com.eicas.common.annotation.FormatContentType;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jsq
 * @since 2021-07-28
 * <p>
 * 处理请求content-type
 */

@Component
public class FormatContentTypeInterceptor implements HandlerInterceptor {
    //标记名称
    public static final String FORMAT_CONTENT_TYPE = "FORMAT_CONTENT_TYPE";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
//            final Method method = handlerMethod.getMethod();
            // 判断是否在类对象上添加了注解
            if (clazz.isAnnotationPresent(FormatContentType.class)) {
                // 设置此请求返回体，需要包装，往下传递，在ResponseBodyAdvice接口进行判断
                response.setContentType("application/json;charset=UTF-8");
                request.setAttribute(FORMAT_CONTENT_TYPE, clazz.getAnnotation(FormatContentType.class));
            }
        }
        return true;
    }
}
