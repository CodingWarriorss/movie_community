package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.dto.CreateMemberDto;
import com.codeworrisors.Movie_Community_Web.dto.UpdateMemberDto;
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


    @PostMapping("/auth/checkid")
    public int checkId(@RequestBody Member member) {
        try {
            memberService.validateDuplicateMemberId(member.getMemberName());
        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            return FAIL;
        }
        return SUCCESS;
    }

    @PostMapping("/auth/join")
    public int joinMember(CreateMemberDto createMemberDto) {
        try {
            System.out.println();
            memberService.createMember(createMemberDto);
        } catch (IllegalStateException e) {
            logger.error(e.getMessage());
            return FAIL;
        }
        return SUCCESS;
    }

    @GetMapping("/api/member")
    public JSONObject readMember(@AuthenticationPrincipal PrincipalDetails userDetail) {
        JSONObject response = new JSONObject();

        logger.info( userDetail.getEmail() );

        try {
            response.put("result", SUCCESS);
            response.put("member", memberService.selectMember(userDetail.getMember()));
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            response.put("result", FAIL);
        }

        return response;
    }

    @PutMapping("/api/member")
    public JSONObject modifyMember(@AuthenticationPrincipal PrincipalDetails userDetail,
                            @ModelAttribute UpdateMemberDto updateMemberDto) {
        JSONObject response = new JSONObject();

        try {
            response.put("result", SUCCESS);
            response.put("member", memberService.updateMember(userDetail.getMember(), updateMemberDto));
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            response.put("result", FAIL);
        }

        return response;
    }

    @DeleteMapping("api/member")
    public int withdrawMember(@AuthenticationPrincipal PrincipalDetails userDetail) {
        try {
            memberService.deleteMember(userDetail.getMember());
        } catch (NoSuchElementException e) {
            logger.error(e.getMessage());
            return FAIL;
        }
        return SUCCESS;
    }
}
