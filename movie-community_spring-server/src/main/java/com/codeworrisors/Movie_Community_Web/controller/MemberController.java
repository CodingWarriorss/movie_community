package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.dto.member.request.CreateMemberDto;
import com.codeworrisors.Movie_Community_Web.dto.member.request.ReadMemberDto;
import com.codeworrisors.Movie_Community_Web.dto.member.request.UpdateMemberDto;
import com.codeworrisors.Movie_Community_Web.dto.member.response.MemberSelectResponseDto;
import com.codeworrisors.Movie_Community_Web.dto.member.response.MemberUpdateResponseDto;
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
    public MemberSelectResponseDto readMember(@ModelAttribute ReadMemberDto readMemberDto) {
        return memberService.selectMember(readMemberDto);
    }

    @PutMapping("/api/member")
    public MemberUpdateResponseDto modifyMember(@AuthenticationPrincipal PrincipalDetails userDetail,
                                                @ModelAttribute UpdateMemberDto updateMemberDto) {
        return memberService.updateMember(userDetail.getMember(), updateMemberDto);
    }

    @DeleteMapping("api/member")
    public int withdrawMember(@AuthenticationPrincipal PrincipalDetails userDetail) {
        memberService.deleteMember(userDetail.getMember());
        return SUCCESS;
    }
}
