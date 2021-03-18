package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public int checkId(String memberName) {
        System.out.println("MemberServiceImpl의 joinMember() 호출");
        try {
            validateDuplicateMemberId(memberName);
        } catch (IllegalStateException e) {
            return 0;//이미 존재하는 아이디
        }
        return 1;
    }

    @Override
    public int joinMember(Member member) {
        System.out.println("MemberServiceImpl의 joinMember() 호출");
        try {
            validateDuplicateMemberId(member.getMemberName());
        } catch (IllegalStateException e) {
            return 0;//이미 존재하는 아이디
        }
        memberRepository.save(member);
        return 1;
    }

    private void validateDuplicateMemberId(String memberName) {
        memberRepository.findByMemberName(memberName)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원");
                });
    }

    @Override
    public int loginMember(String memberName, String password) {
        try {
            validateDuplicateMemberId(memberName);
        } catch (IllegalStateException e) {
            Member m = memberRepository.findByMemberName(memberName).get();
            return m.getMemberName().equals(memberName) && m.getPassword().equals(password) ? 1 : 0; // 로그인 성공(1), 로그인 실패(0)
        }
        return -1;// 존재하지 않는 ID(-1)
    }

    @Override
    public void updateMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void withdrawMember(String memberName) {
        Member member = memberRepository.getOne(memberName);
        memberRepository.delete(member);
    }
}
