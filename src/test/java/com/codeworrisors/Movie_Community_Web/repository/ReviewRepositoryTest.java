package com.codeworrisors.Movie_Community_Web.repository;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.model.Review;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// properties에 설정한 Datasource를 사용하겠다. (Replace.ANY : 내장 DB사용)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReviewRepositoryTest {
    static Member member;

    @Autowired
    ReviewRepository reviewRepository;

    @BeforeAll
    static void init() {
        member = new Member();
        member.setMemberId("anyeji1220");
    }

    @AfterEach
    void clear(){
        reviewRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("새로운 리뷰 저장")
    void createTest() {
        Review review = new Review();
        review.setMember(member);
        review.setContent("content");
        review.setMovieTitle("title");
        review.setImageUrl("url");

        Review saved = reviewRepository.save(review);

        assertEquals(saved.getReviewId(), review.getReviewId());
    }

    @Test
    @Order(2)
    @DisplayName("리뷰 수정")
    void updateTest() {
        Review review = new Review();
        review.setMember(member);
        review.setContent("content");
        review.setMovieTitle("title");
        review.setImageUrl("url");
        Review saved = reviewRepository.save(review);

        Review review2 = new Review();
        review2.setReviewId(saved.getReviewId());
        String new_content = "리뷰 내용 수정";
        review2.setContent(new_content);
        Review saved2 = reviewRepository.save(review2);

        assertEquals(saved2.getContent(), new_content);
    }

    @Test
    @Order(3)
    @DisplayName("리뷰 삭제")
    void deleteTest() {
        Review review = new Review();
        review.setMember(member);
        review.setContent("content");
        review.setMovieTitle("title");
        review.setImageUrl("url");

        Review saved = reviewRepository.save(review);
        reviewRepository.delete(saved);

        Assertions.assertTrue(reviewRepository.findById(saved.getReviewId()).isEmpty());
    }
}