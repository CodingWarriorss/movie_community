package com.codeworrisors.Movie_Community_Web;

import com.codeworrisors.Movie_Community_Web.config.AppProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MovieCommunityWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCommunityWebApplication.class, args);
	}

}
