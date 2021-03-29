package com.codeworrisors.Movie_Community_Web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MovieCommunityWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCommunityWebApplication.class, args);
	}

}
