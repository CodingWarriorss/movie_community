package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.service.MemberService;
import com.codeworrisors.Movie_Community_Web.config.auth.PrincipalDetails;
import org.json.simple.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
public class MemberController {
    private static int SUCCESS = 1;
    private static int FAIL = 0;

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    /*
     * 아이디 중복체크
     * response (1-성공, 0-이미 존재하는 아이디)
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
     * response (1-성공, 0-이미 존재하는 아이디)
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
     * response (성공/실패 여부, 회원정보)
     * */
    @GetMapping("/api/member")
    public JSONObject readMember(@AuthenticationPrincipal PrincipalDetails userDetail) {
        JSONObject response = new JSONObject();
        response.put("result", "SUCCESS");

        try {
            response.put("member", memberService.selectMember(userDetail.getUsername()));
        } catch (NoSuchElementException e) {
            response.put("result", "FAIL");
        }

        return response;
    }

    /*
     * 회원정보 수정
     * response (1-성공, 0-존재하지 않는 회원)
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
     * response (1-성공, 0-존재하지 않는 회원)
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
