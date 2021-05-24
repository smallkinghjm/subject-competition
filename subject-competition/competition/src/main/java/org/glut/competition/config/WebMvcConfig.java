package org.glut.competition.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册拦截器
        InterceptorRegistration ir = registry.addInterceptor(loginInterceptor);
        // 添加拦截请求
        ir.addPathPatterns("/**");
        // 添加不拦截的请求
        ir.excludePathPatterns("/user/login")
                .excludePathPatterns("/user/login-page")
                .excludePathPatterns("/password/**")
                .excludePathPatterns("/admin/login-page")
                .excludePathPatterns("/admin/login")
                .excludePathPatterns("/user/forgetPassword")
                .excludePathPatterns("/user/forgetPassword-page")
                .excludePathPatterns("/css/**","/js/**","/layui/**","/images/**");


    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        //指向外部目录
        registry.addResourceHandler("contentFile/**").addResourceLocations("file:D:/contentFile/");
    }
}


