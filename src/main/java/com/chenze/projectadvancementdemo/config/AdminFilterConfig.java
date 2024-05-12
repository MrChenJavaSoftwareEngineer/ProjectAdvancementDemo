package com.chenze.projectadvancementdemo.config;

import com.chenze.projectadvancementdemo.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class AdminFilterConfig {
    @Bean
    public AdminFilter adminFilter(){
        return new AdminFilter();
    }

    @Bean(name = "AdminFilterConfig")
    public FilterRegistrationBean filterRegistrationBean(){
         FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
         filterFilterRegistrationBean.setFilter(adminFilter());
         filterFilterRegistrationBean.addUrlPatterns("/admin/*");
         filterFilterRegistrationBean.setName("AdminFilterConfig");
         return filterFilterRegistrationBean;
    }
}
