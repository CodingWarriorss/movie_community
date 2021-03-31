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

    /*
     * 아이디 중복체크
     */
    @PostMapping("checkid")
    public int checkId(@RequestBody Member member) {
        try {
            memberService.validateDuplicateMemberId(member.getMemberName());
        } catch (IllegalStateException e) {
            return FAIL;
        }
        return SUCCESS;
    }

    /*
     * 회원가입
     * */
    @PostMapping("join")
    public int joinMember(@RequestBody Member member) {
        try {
            memberService.createMember(member);
        } catch (IllegalStateException e) {
            return FAIL;
        }
        return SUCCESS;
    }

    /*
     * 회원정보 조회
     * */
    @GetMapping("/api/member")
    public int readMember(@AuthenticationPrincipal PrincipalDetails userDetail) {
        try {
            memberService.selectMember(userDetail.getUsername());
        } catch (NoSuchElementException e) {
            return FAIL;
        }
        return SUCCESS;
    }

    /*
     * 회원정보 수정
     * */
    @PutMapping("/api/member")
    public int modifyMember(@RequestBody Member member) {
        try {
            memberService.updateMember(member);
        } catch (NoSuchElementException e) {
            return FAIL;
        }
        return SUCCESS;
    }

    /*
     * 회원정보 삭제
     * */
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
