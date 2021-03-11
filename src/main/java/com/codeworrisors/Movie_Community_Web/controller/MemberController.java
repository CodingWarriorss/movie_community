package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.service.MemberService;
import com.codeworrisors.Movie_Community_Web.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/members")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private int id = 0;// 임시

    /*
    * 회원가입
    * 중복체크 : 1 (성공), 0 (이미 존재하는 아이디)
    * */
    @PostMapping("/join")
    public int createMember(@RequestBody Member member){
        System.out.println(member);
        member.setId(id++); // 임시
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole("ROLE_USER");
        int res = memberService.joinMember(member);
        if (res == 1){
            System.out.println("회원가입 성공");
        } else{
            System.out.println("이미 존재하는 아이디");
        }

        return res;
    }

    /*
    * 로그인
    * 1 (성공), 0 (비밀번호 틀림), -1 (존재하지 않는 아이디)
    * */
//    @PostMapping(value = "/login")
//    public int loginMember(@RequestBody Member member){
//        int res = memberService.loginMember(member.getMemberName(), member.getPassword());
//        if (res == 1){
//            System.out.println("로그인 성공");
//        }else if (res == 0){
//            System.out.println("비밀번호 오류");
//        }else{
//            System.out.println("존재하지 않는 아이디");
//        }
//
//        return res;
//    }

    /*
    * 회원정보 수정
    * */
    @PutMapping
    public void updateMember(@RequestBody Member member){
        memberService.updateMember(member);
    }

    /*
    * 회원정보 삭제
    * */
    @DeleteMapping
    public void withdrawMember(@RequestBody String id){
        memberService.withdrawMember(id);
    }
}
