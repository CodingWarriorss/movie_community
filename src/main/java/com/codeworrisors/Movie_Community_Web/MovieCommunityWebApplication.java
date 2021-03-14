package com.codeworrisors.Movie_Community_Web;

import com.codeworrisors.Movie_Community_Web.config.AppProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MovieCommunityWebApplication {

	public static final Logger logger = LoggerFactory.getLogger(MovieCommunityWebApplication.class);
	public static void main(String[] args) {

		logger.info("저의 스프링이 시작되었스빈다. 어떻게 이거 안보이려나?");
		SpringApplication.run(MovieCommunityWebApplication.class, args);
	}

}
