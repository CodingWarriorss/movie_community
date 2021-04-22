package com.codeworrisors.Movie_Community_Web.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.BDDAssertions.then;


class MemberTest {
    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member(1L, "id", "password", "name", "email", "bio", "website", "profileImage", RoleType.ROLE_USER);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,false", "2,true", "3,true"})
    @DisplayName("다른 멤버인지 확인하는 메서드")
    void isNotSameMember(Long memberId, boolean expect) {
        //When
        boolean sameMemberResult = member.isNotSameMember(memberId);
        //Then
        then(sameMemberResult).isEqualTo(expect);
    }

    @Test
    @DisplayName("멤버의 이름을 변경하면 변경된 변경된 이름을 리턴합니다")
    void changeName_return_value() {
        //when
        String changedMemberName = member.changeName("changedName");

        //then
        then(changedMemberName).isEqualTo("changedName");
    }

    @Test
    @DisplayName("멤버의 이름을 변경하면 멤버의 속성이 변경됩니다.")
    void changeName_attribute_change() {
        //when
        member.changeName("changedName");

        //then
        then(member.getName()).isEqualTo("changedName");
    }

    @Test
    @DisplayName("멤버의 이메일을 변경하면 변경된 값을 리턴합니다.")
    void changeEmail_return_value() {
        //when
        String changedEmail = member.changeEmail("changeEmail");

        //then
        then(changedEmail).isEqualTo("changeEmail");
    }

    @Test
    @DisplayName("멤버의 이메일을 변경하면 멤버의 이메일 속성이 변경됩니다.")
    void changeEmail_attribute_change() {
        //when
        member.changeEmail("changeEmail");

        //then
        then(member.getEmail()).isEqualTo("changeEmail");
    }

    @Test
    @DisplayName("멤버의 웹사이트를 변경하면 변경된 값을 리턴합니다.")
    void changeWebsite_return_value() {
        //when
        String changedWebsite = member.changeWebsite("changeWebsite");

        //then
        then(changedWebsite).isEqualTo("changeWebsite");
    }

    @Test
    @DisplayName("멤버의 웹사이트를 변경하면 멤버의 속성이 변경됩니다.")
    void changeWebsite_attribute_change() {
        //when
        member.changeWebsite("changeWebsite");

        //then
        then(member.getWebsite()).isEqualTo("changeWebsite");
    }
    @Test
    @DisplayName("멤버의 자기 소개를 변경하면 변경된 값을 리턴합니다.")
    void changeBio_return_value() {
        //when
        String changeBio = member.changeBio("changeBio");

        //then
        then(changeBio).isEqualTo("changeBio");
    }

    @Test
    @DisplayName("멤버의 자기 소개를 변경하면 멤버의 속성을 변경합니다.")
    void changeBio_attribute_change() {
        //when
        member.changeBio("changeBio");

        //then
        then(member.getBio()).isEqualTo("changeBio");
    }

    /*
    @Test
    @DisplayName("멤버의 프로필을 변경하면 변경된 값을 리턴합니다.")
    void changeProfile_return_value() throws FileNotFoundException, IOException {
       MultipartFile profileImg = new MockMultipartFile("changeProfile.png", new FileInputStream(new File("/Users/heesang/dev/dev_web/images/changeProfile.png")));

       String changeProfile = member.changeProfile(profileImg);

       then(changeProfile).isEqualTo("changeProfile");
    }

    @Test
    @DisplayName("멤버의 프로필을 변경하면 멤버의 속성이 변경됩니다.")
    void changeProfile_parameter_null() {

    }

    @Test
    @DisplayName("프로필의 속성이 null이면 기존의 속성을 유지합니다.")
    void changeProfile_attribute_change() {

    }*/
}