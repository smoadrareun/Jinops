package com.hebeu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: MvcConfig
 * @Author: Smoadrareun
 * @Description: TODO 配置静态资源映射
 */

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
                registry.addResourceHandler("/**").addResourceLocations("classpath:/templates/");
            }
        };
    }
}
