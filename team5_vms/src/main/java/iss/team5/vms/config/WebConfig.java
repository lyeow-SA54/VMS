package iss.team5.vms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import iss.team5.vms.interceptor.LoginHandlerInterceptor;

@Component
public class WebConfig implements WebMvcConfigurer{

    @Autowired
    LoginHandlerInterceptor loginInt;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInt)
        .addPathPatterns("/admin/**","/student/**");
    }

}
