package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.service.MemberService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class MemberController {

	Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /*
    아이디 중복체크: 1 (성공), 0 (이미 존재하는 아이디)
    */
    @PostMapping("checkid")
    public int checkId(@RequestBody Member member) {
        logger.info("내가 지금 하고 있는거 콜 했습니다.");
        return memberService.checkId(member.getMemberName());
    }


    /*
     * 회원가입
     * 한번 더 중복체크: 1 (성공), 0 (이미 존재하는 아이디)
     * */
    @PostMapping("join")
    public int createMember(@RequestBody Member member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole("ROLE_USER");
        int res = memberService.joinMember(member);
        if (res == 1) {
            System.out.println("회원가입 성공");
        } else {
            System.out.println("이미 존재하는 아이디");
        }

        return res;
    }

    /*
     * 회원정보 수정
     * */
    @PutMapping
    public void updateMember(@RequestBody Member member) {
        memberService.updateMember(member);
    }

    /*
     * 회원정보 삭제
     * */
    @DeleteMapping
    public void withdrawMember(@RequestBody String id) {
        memberService.withdrawMember(id);
    }
}
