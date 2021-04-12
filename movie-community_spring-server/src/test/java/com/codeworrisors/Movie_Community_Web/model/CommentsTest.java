package com.codeworrisors.Movie_Community_Web.model;

import com.codeworrisors.Movie_Community_Web.exception.NoAuthCommentStateException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CommentsTest {
    private Comments comments;

    @BeforeEach
    void setUp() {
        comments = new Comments();
        Member member = new Member();
        member.setId(1L);
        comments.setMember(member);
    }

    @Test
    @DisplayName("같은 멤버의 댓글이면 업데이트를 진행하여 객체 자신을 반환합니다.")
    void updateContents() {
        //Given

        //When
        Comments result = comments.updateContents(1L, "test");

        //Then
        assertThat(result.getContent()).isEqualTo("test");
    }

    @Test
    @DisplayName("다른 멤버의 댓글이면 예외를 반환합니다.")
    void updateContents_throw_exception() {
        //When
        assertThatThrownBy(() -> comments.updateContents(2L, "test"))
                .isInstanceOf(NoAuthCommentStateException.class)
                .hasMessageContaining("권한 없는 댓글");
    }
}