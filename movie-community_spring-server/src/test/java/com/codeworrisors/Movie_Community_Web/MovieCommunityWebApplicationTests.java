package com.codeworrisors.Movie_Community_Web;

import static org.mockito.ArgumentMatchers.contains;

import java.io.File;
import java.util.Arrays;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.repository.ReviewRepository;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
class MovieCommunityWebApplicationTests {

	

	@Autowired
	ReviewRepository reviewRepository;

	
	@Test
	void contextLoads() {
		
	}

}
