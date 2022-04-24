package com.eicas.security.config;

import com.eicas.security.handler.*;
import com.eicas.security.service.impl.AuthUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * @author osnudt
 * @since 2022-04-19
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /*
     * 登录成功处理逻辑
     */
    @Resource
    CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    /*
     * 登录失败处理逻辑
     */
    @Resource
    CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    /*
     * 权限拒绝处理逻辑
     */
    @Resource
    CustomizeAccessDeniedHandler accessDeniedHandler;

    /*
     * 匿名用户访问无权限资源时的异常
     */
    @Resource
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;

    /*
     * 会话失效(账号被挤下线)处理逻辑
     */
    @Resource
    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    /*
     * 登出成功处理逻辑
     */
    @Resource
    CustomizeLogoutSuccessHandler logoutSuccessHandler;

//    /*
//     * 访问决策管理器，动态权限管理需实现该类
//     */
//    @Resource
//    CustomizeAccessDecisionManager accessDecisionManager;

    // 实现动态权限拦截
//    @Resource
//    CustomizeFilterInvocationSecurityMetadataSource securityMetadataSource;
    /*
     * 权限拦截器
     */
    @Resource
    private CustomizeAbstractSecurityInterceptor securityInterceptor;

    /*
     * 获取用户账号密码及权限信息
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new AuthUserServiceImpl();
    }

    /*
     * 设置默认的加密方式（强hash方式加密）
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .and()
                .formLogin()
                .permitAll()
                .failureHandler(authenticationFailureHandler)
                .successHandler(authenticationSuccessHandler)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and().sessionManagement().maximumSessions(1).
                expiredSessionStrategy(sessionInformationExpiredStrategy);
    }
}
