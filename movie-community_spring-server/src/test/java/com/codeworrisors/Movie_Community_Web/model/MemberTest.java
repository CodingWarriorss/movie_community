package com.codeworrisors.Movie_Community_Web.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @ParameterizedTest
    @CsvSource(value = {"1,false", "2,true", "3,true"})
    @DisplayName("다른 멤버인지 확인하는 메서드")
    void isNotSameMember(Long memberId, boolean expect) {
        //Given
        Member member = new Member();
        member.setId(1L);

        //When
        boolean sameMemberResult = member.isNotSameMember(memberId);

        //Then
        //assertThat(sameMemberResult).isEqualTo(expect);
        then(sameMemberResult).isEqualTo(expect);
    }
}