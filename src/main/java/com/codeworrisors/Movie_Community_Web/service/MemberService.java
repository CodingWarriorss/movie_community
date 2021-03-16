package com.codeworrisors.Movie_Community_Web.service;


import com.codeworrisors.Movie_Community_Web.model.Member;

public interface MemberService {
    int joinMember(Member member);
    int loginMember(String memberName, String password);
    void updateMember(Member member);
    void withdrawMember(String memberName);
}
