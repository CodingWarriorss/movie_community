package com.codeworrisors.Movie_Community_Web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
public class WebConfig implements WebMvcConfigurer{

    // 어떤 부분이 이전 설정을 하는 부분인지 보고 이후 안쓰는 아래 코드는 삭제 요망.
    // @Bean
    // public CorsFilter corsFilter(){
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     CorsConfiguration config = new CorsConfiguration();
    //     config.setAllowCredentials(true); // 자바 스크립트 json 처리 허용
    //     config.setAllowedOriginPatterns(Collections.singletonList("*")); // 모든 ip에 응답 허용
    //     config.setAllowedHeaders(Collections.singletonList("*")); // 모든 header에 응답 허용
    //     config.setAllowedMethods(Collections.singletonList("*")); // 모든 (POST, GET, PUT, POST, DELETE..) 요청에 응답 허용
    //     List<String> exposedHeaders = new ArrayList<>();
    //     exposedHeaders.add("Authorization");
    //     config.setExposedHeaders(exposedHeaders);
    //     source.registerCorsConfiguration("/**", config);
    //     return new CorsFilter(source);

    // }

    private final long MAX_AGE_SECS = 3600;

    /*
        기존 @Bean으로 CORS관련 이슈를 해결하기 위한 설정을 
        WebMvcConfigurer구현 하는 방식으로 변경하면서 아래와 같이 설정
    */

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //현재는 테스트를 위해 모든 경우를 허용하고 있지만 이후 필요한 부분만 엄격하게? 설정할것.
        registry.addMapping("/**")
            .allowCredentials(true)
            .allowedHeaders("*")
            .allowedMethods("*")
            .allowedOrigins("*")
            .exposedHeaders("*")
            .maxAge(MAX_AGE_SECS);
    }

    /*
        Static resource 관련 설정 Method

    */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("classpath:/image")
        .addResourceLocations("/image");
    }
}
