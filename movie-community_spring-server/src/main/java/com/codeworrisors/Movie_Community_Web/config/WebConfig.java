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
        기존 @Bean으로 CORS관련 이슈를 해결하기 위한 설정을 
        WebMvcConfigurer구현 하는 방식으로 변경하면서 아래와 같이 설정하려 했으나
        ?? addCorsMappings를 Overriding 해서는 cors 관련 필터가 작동하지 않는것으로 보입니다. ....
    */

    // @Override
    // public void addCorsMappings(CorsRegistry registry) {
    //     //현재는 테스트를 위해 모든 경우를 허용하고 있지만 이후 필요한 부분만 엄격하게? 설정할것.

    //     System.out.println("여기가 실행되면 허용 CORS 를 잘 설정한거 아니나굥?");
    //     registry.addMapping("/**")
    //         .allowCredentials(true)
    //         .allowedHeaders("*")
    //         .allowedMethods("*")
    //         .allowedOrigins("http://localhost:3000")
    //         .allowedOriginPatterns("http://localhost:3000")
    //         .exposedHeaders("*");
    // }

    /*
        Static resource 관련 설정 Method
    */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("classpath:/image") //이 경로로 연결시킴
        .addResourceLocations("/image");                //이런 패턴의 요청은
    }
}
