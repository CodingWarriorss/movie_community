package com.codeworrisors.Movie_Community_Web.controller;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.service.MemberService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@CrossOrigin("*")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    
    /*
    * 아이디 중복체크
    * 1 (사용가능), 0 (이미 존재하는 아이디)
    * */
    @PostMapping("/checkid")
    public int checkId(@RequestBody String id){
        int res = memberService.checkId(id);
        if (res == 1){
            System.out.println("중복체크 : 사용가능한 아이디");
        }
        else{
            System.out.println("중복체크 : 이미 존재하는 아이디");
        }
        
        return res;
    }
    

    /*
    * 회원가입
    * 중복체크 : 1 (성공), 0 (이미 존재하는 아이디)
    * */
    @PostMapping("/join")
    public int createMember(@RequestBody Member member){
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
    @PostMapping(value = "/login")
    public int loginMember(@RequestBody Member member){
        int res = memberService.loginMember(member.getMemberId(), member.getMemberPassword());
        if (res == 1){
            System.out.println("로그인 성공");
        }else if (res == 0){
            System.out.println("비밀번호 오류");
        }else{
            System.out.println("존재하지 않는 아이디");
        }

        return res;
    }

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
