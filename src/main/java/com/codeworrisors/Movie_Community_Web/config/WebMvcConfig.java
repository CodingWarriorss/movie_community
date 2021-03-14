package com.codeworrisors.Movie_Community_Web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 *  CORS? 관련 설정
 * 크로스 도메인 관련한 설정에서 필요한 부분으로 이해하고 있습니다.
 * 
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        /**
         * 기존에 있는 코드를 사용시에 오류
         * 시키는데로 allowCredentials을 true로 설정시에 allowedOrigins에 패턴이 '*'을 사용할 수 없다고 합니다
         * 그래서 지정된 uri 패턴으로 사용시에 는 cors 이슈를 잘 해결합니다.
         */
        registry.addMapping("/**")
        .allowedOrigins("http://localhost:3000")
        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        //.allowCredentials(true)   //오류를 맛보고 싶다면 주석해제
        .maxAge(MAX_AGE_SECS);
    }
}
