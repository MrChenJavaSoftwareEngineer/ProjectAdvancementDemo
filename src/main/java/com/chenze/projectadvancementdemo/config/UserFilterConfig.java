package com.chenze.projectadvancementdemo.config;

import com.chenze.projectadvancementdemo.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class UserFilterConfig {
    @Bean
    public UserFilter userFilter(){
        return new UserFilter();
    }
    @Bean(name = "UserFilterConfig")
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(userFilter());
        filterFilterRegistrationBean.addUrlPatterns("/User/*");
        filterFilterRegistrationBean.setName("UserFilterConfig");
        return filterFilterRegistrationBean;
    }
}
