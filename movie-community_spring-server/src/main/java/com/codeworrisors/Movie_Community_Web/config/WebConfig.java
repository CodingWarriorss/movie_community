package com.codeworrisors.Movie_Community_Web.config;

import com.codeworrisors.Movie_Community_Web.property.StaticResourceProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
    Static resource 관련 설정 Method
*/

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(StaticResourceProperties.IMAGE_REQUEST_URL + "/**")
                .addResourceLocations("file:///" + StaticResourceProperties.IMAGE_UPLOAD_PATH);
    }
}
