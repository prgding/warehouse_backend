package com.warehouse.config;

import com.warehouse.filter.SecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 原生Servlet的配置类:
 */
@Configuration
public class ServletConfig {

    /**
     * 注册原生Servlet的Filter
     */
    @Bean
    public FilterRegistrationBean securityFilter() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        //创建SecurityFilter对象
        SecurityFilter securityFilter = new SecurityFilter();
        //注册SecurityFilter
        filterRegistrationBean.setFilter(securityFilter);
        //配置SecurityFilter拦截所有请求
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
