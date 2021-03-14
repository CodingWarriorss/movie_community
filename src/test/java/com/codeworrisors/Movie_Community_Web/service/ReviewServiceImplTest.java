package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewServiceImplTest {

    @Autowired
    ReviewRepository reviewRepository;
    ReviewService reviewService;

    @BeforeEach
    void init(){
        reviewService = new ReviewServiceImpl(reviewRepository);
    }

    @Test
    void createReview() {
        Member member = new Member();
        member.setMemberId("asdf");
        member.setMemberPassword("pwd");
        member.setMemberBirth("asdf");
        member.setMemberEmail("@@");
        member.setMemberAddress("asdfwer");
        member.setMemberGender("g");
        member.setMemberPhone("111");

        Review review = new Review();
        review.setMember(member);
        review.setContent("content");
        review.setMovieTitle("title");
        review.setImageUrl("url");

        reviewService.createReview(review);

        Review review1 = reviewRepository.findById(1).get();
        Assertions.assertEquals(review.getReviewId(), review1.getReviewId());
    }
}