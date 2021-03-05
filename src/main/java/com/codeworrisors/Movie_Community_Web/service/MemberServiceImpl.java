package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void join(Member member) {
        validateDuplicateMemberId(member);
        memberRepository.save(member);
    }

    private void validateDuplicateMemberId(Member member) {
        memberRepository.findByMemberId(member.getMemberId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    @Override
    public int authenticateMember(String id, String password) {
        Member m = memberRepository.findByMemberIdAndMemberPassword(id, password).get();
        return m.getMemberId().equals(id) && m.getMemberPassword().equals(password) ? 1 : 0;
    }
}
