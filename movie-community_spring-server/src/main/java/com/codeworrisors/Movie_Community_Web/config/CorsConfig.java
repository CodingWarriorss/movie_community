package com.codeworrisors.Movie_Community_Web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.*;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 자바 스크립트 json 처리 허용
        config.setAllowedOriginPatterns(Collections.singletonList("*")); // 모든 ip에 응답 허용
        config.setAllowedHeaders(Collections.singletonList("*")); // 모든 header에 응답 허용
        config.setAllowedMethods(Collections.singletonList("*")); // 모든 (POST, GET, PUT, POST, DELETE..) 요청에 응답 허용
        List<String> exposedHeaders = new ArrayList<>();
        exposedHeaders.add("Authorization");
        config.setExposedHeaders(exposedHeaders);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);

    }
    
}
