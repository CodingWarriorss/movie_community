package com.codeworrisors.Movie_Community_Web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
    Web 관련 설정 Configuration 
    기존에는 Bean생성으로 설정을
    WebMvcConfigurer 구현으로 재설정
    -> Override로 좀더 직관적으로? 설정되는 느낌적 느낌
*/
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{

    private final long MAX_AGE_SECS = 3600;

    /*
        Static resource 관련 설정 Method
    */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("classpath:/image") //이 경로로 연결시킴
        .addResourceLocations("/image");                //이런 패턴의 요청은
    }
}
