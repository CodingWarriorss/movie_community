package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.service.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(value = "/")
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
