package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import com.codeworrisors.Movie_Community_Web.repository.ReviewRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewServiceImplTest {
    static Member member;

    @Autowired
    ReviewRepository reviewRepository;
    ReviewServiceImpl reviewService;

    @BeforeAll
    static void init() { // @BeforeAll은 클래스 메소드여야 한다.
        member = new Member();
        member.setMemberId("anyeji1220");
    }

    @BeforeEach
    void before() {
        // init()에서 만들면 클래스 변수로 설정해야 하는데, 그러면 Repository 세팅 전에 생성되기 때문에 오류
        // 필드에서 만들면 스프링컨테이너가 Repository를 세팅하기 전에 생성하기 때문에 오류
        reviewService = new ReviewServiceImpl(reviewRepository);
    }

    @AfterEach
    void clear() {
        reviewService.clear();
    }

    @Test
    @DisplayName("새로운 리뷰 저장")
    void createTest() {
        Review review = new Review();
        review.setMember(member);
        review.setContent("content");
        review.setMovieTitle("title");
        review.setImageUrl("url");

        Review saved = reviewService.createReview(review);
        assertEquals(saved.getReviewId(), review.getReviewId());
    }

    @Test
    @DisplayName("존재하는 리뷰 수정")
    void updateTest() {
        Review review = new Review();
        review.setMember(member);
        review.setContent("content");
        review.setMovieTitle("title");
        review.setImageUrl("url");
        Review saved = reviewService.createReview(review);

        Review review2 = new Review();
        review2.setReviewId(saved.getReviewId());
        String new_content = "리뷰 내용 수정";
        review2.setContent(new_content);
        Review updated = reviewService.updateReview(review2);

        assertEquals(updated.getContent(), new_content);
    }

    @Test
    @DisplayName("존재하지 않는 리뷰 수정")
    void updateTest2() {
        Review review = new Review();
        review.setReviewId(15000);

        Review updated = reviewService.updateReview(review);
        assertNull(updated);
    }

    @Test
    @DisplayName("리뷰 삭제")
    void deleteTest() {
        Review review = new Review();
        review.setMember(member);
        review.setContent("content");
        review.setMovieTitle("title");
        review.setImageUrl("url");

        Review saved = reviewService.createReview(review);
        reviewRepository.delete(saved);

        Assertions.assertTrue(reviewRepository.findById(saved.getReviewId()).isEmpty());
    }
}