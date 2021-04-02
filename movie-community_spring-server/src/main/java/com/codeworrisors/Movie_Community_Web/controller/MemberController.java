package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.service.MemberService;
import com.codeworrisors.Movie_Community_Web.security.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private static int SUCCESS = 1;
    private static int FAIL = 0;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MemberService memberService;


    @PostMapping("checkid")
    public int checkId(@RequestBody Member member) {
        try {
            memberService.validateDuplicateMemberId(member.getMemberName());
        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            return FAIL;
        }
        return SUCCESS;
    }

    @PostMapping("join")
    public int joinMember(@RequestBody Member member) {
        try {
            memberService.createMember(member);
        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            return FAIL;
        }
        return SUCCESS;
    }

    @GetMapping("/api/member")
    public JSONObject readMember(@AuthenticationPrincipal PrincipalDetails userDetail) {
        JSONObject response = new JSONObject();

        try {
            response.put("result", SUCCESS);
            response.put("member", memberService.selectMember(userDetail.getUsername()));
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            response.put("result", FAIL);
        }
        return response;
    }

    @PutMapping("/api/member")
    public int modifyMember(@RequestBody Member member) {
        try {
            memberService.updateMember(member);
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            return FAIL;
        }
        return SUCCESS;
    }

    @DeleteMapping("api/member")
    public int withdrawMember(@RequestBody Member member) {
        try {
            memberService.deleteMember(member.getMemberName());
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            return FAIL;
        }
        return SUCCESS;
    }
}
