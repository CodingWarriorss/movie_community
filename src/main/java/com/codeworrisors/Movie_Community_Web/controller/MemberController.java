package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@CrossOrigin("*")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping(value = "/join")
    public void createMember(@RequestBody Member member){
        memberService.join(member);
    }

    @PostMapping(value = "/login")
    public int loginMember(@RequestBody Member member){
        int res = memberService.authenticateMember(member.getMemberId(), member.getMemberPassword());
        System.out.println(res);
        return res;
    }
}
