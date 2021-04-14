package com.codeworrisors.Movie_Community_Web.model;

import com.codeworrisors.Movie_Community_Web.exception.NoAuthReviewStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {
    private Review review;

    @BeforeEach
    void setUp() {
        //Given
        Member member = new Member();
        member.setId(1L);
        review = new Review();
        review.setMember(member);
        review.setContent("가나다라");
        review.setRating(1);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,수정,2"})
    @DisplayName("멤버의 권한을 검사하고 리뷰 콘텐츠와 별점을 수정합니다.")
    void updateReview(Long memberId, String updateContent, int updateRating) {
        //When
        Review updateReview = review.updateReview(memberId, updateContent, updateRating);

        //Then
        assertThat(updateReview.getContent()).isEqualTo(updateContent);
        assertThat(updateReview.getRating()).isEqualTo(updateRating);
    }

    @Test
    @DisplayName("권한이 다른 멤버는 예외를 발생시킵니다.")
    void updateReview_throws_exception() {
        assertThatThrownBy(()-> review.updateReview(2L, "test", 2))
                .isInstanceOf(NoAuthReviewStateException.class)
                .hasMessageContaining("리뷰에 관한 권한이 없음");
    }

    @Test
    @DisplayName("Review 멤버의 권한을 확인하고 맞으면 객체를 반환합니다.")
    void validateReviewAuth() {
        Review validateReview = review.validateReviewAuth(1L);

        assertThat(validateReview.getMember().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("review 멤버의 권한을 확인하고 다르면 예외를 발생시킵니다.")
    void valdiateReviewAuth_throw_exception() {
        assertThatThrownBy(() -> review.validateReviewAuth(2L))
                .isInstanceOf(NoAuthReviewStateException.class)
                .hasMessageContaining("리뷰에 관한 권한이 없음");
    }

}