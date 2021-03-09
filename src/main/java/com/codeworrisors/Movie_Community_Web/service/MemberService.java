package com.codeworrisors.Movie_Community_Web.service;


import com.codeworrisors.Movie_Community_Web.model.Member;

public interface MemberService {
    int checkId(String id);
    int joinMember(Member member);
    int loginMember(String id, String password);
    void updateMember(Member member);
    void withdrawMember(String id);
}
