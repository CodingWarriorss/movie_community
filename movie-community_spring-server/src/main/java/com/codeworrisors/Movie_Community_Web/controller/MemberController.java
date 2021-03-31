package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.service.MemberService;
import com.codeworrisors.Movie_Community_Web.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private static int SUCCESS = 1;
    private static int FAIL = 0;

    private final MemberService memberService;


    @PostMapping("checkid")
    public int checkId(@RequestBody Member member) {
        try {
            memberService.validateDuplicateMemberId(member.getMemberName());
        } catch (IllegalStateException e) {
            return FAIL;
        }
        return SUCCESS;
    }

    @PostMapping("join")
    public int joinMember(@RequestBody Member member) {
        try {
            memberService.createMember(member);
        } catch (IllegalStateException e) {
            return FAIL;
        }
        return SUCCESS;
    }

    @GetMapping("/api/member")
    public int readMember(@AuthenticationPrincipal PrincipalDetails userDetail) {
        try {
            memberService.selectMember(userDetail.getUsername());
        } catch (NoSuchElementException e) {
            return FAIL;
        }
        return SUCCESS;
    }

    @PutMapping("/api/member")
    public int modifyMember(@RequestBody Member member) {
        try {
            memberService.updateMember(member);
        } catch (NoSuchElementException e) {
            return FAIL;
        }
        return SUCCESS;
    }

    @DeleteMapping("api/member")
    public int withdrawMember(@RequestBody Member member) {
        try {
            memberService.deleteMember(member.getMemberName());
        } catch (NoSuchElementException e) {
            return FAIL;
        }
        return SUCCESS;
    }
}
