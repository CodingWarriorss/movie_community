package com.codeworrisors.Movie_Community_Web.service;


import com.codeworrisors.Movie_Community_Web.model.Member;

public interface MemberService {
    void join(Member member);
    int authenticateMember(String id, String password);
//    void modify();
//    void withdraw();
}
