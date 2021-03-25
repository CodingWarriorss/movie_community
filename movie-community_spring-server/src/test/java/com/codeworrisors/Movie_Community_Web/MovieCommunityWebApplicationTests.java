package com.codeworrisors.Movie_Community_Web;

import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.repository.ReviewRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovieCommunityWebApplicationTests {

	

	@Autowired
	ReviewRepository reviewRepository;

	
	@Test
	void contextLoads() {


	}

}
