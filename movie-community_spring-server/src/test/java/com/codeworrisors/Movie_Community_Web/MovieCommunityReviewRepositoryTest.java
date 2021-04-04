package com.codeworrisors.Movie_Community_Web;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MovieCommunityReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void reviewCountTest(){
        Member member = new Member();
        member.setId(1);
        int count = reviewRepository.countReviewByMember(member);

        System.out.println( member.getId() +" 가 쓴 계시물 개수 : " + count );


    }
}
