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

    private MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public int checkId(String id) {
        try {
            validateDuplicateMemberId(id);
        } catch (IllegalStateException e) {
            return 0;//이미 존재하는 아이디
        }
        return 1;// 사용가능한 아이디
    }

    @Override
    public int joinMember(Member member) {
        try {
            validateDuplicateMemberId(member.getMemberId());
        } catch (IllegalStateException e) {
            return 0;//이미 존재하는 아이디
        }
        memberRepository.save(member);
        return 1;
    }

    private void validateDuplicateMemberId(String id) {
        memberRepository.findByMemberId(id)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원");
                });
    }

    @Override
    public int loginMember(String id, String password) {
        try {
            validateDuplicateMemberId(id);
        } catch (IllegalStateException e) {
            Member m = memberRepository.findByMemberId(id).get();
            return m.getMemberId().equals(id) && m.getMemberPassword().equals(password) ? 1 : 0; // 로그인 성공(1), 로그인 실패(0)
        }
        return -1;// 존재하지 않는 ID(-1)
    }

    @Override
    public void updateMember(Member member) {
        memberRepository.save(member);
    }

    @Override
    public void withdrawMember(String id) {
        Member member = memberRepository.getOne(id);
        memberRepository.delete(member);
    }
}
