package com.codeworrisors.Movie_Community_Web.service;

import com.codeworrisors.Movie_Community_Web.model.Member;
import com.codeworrisors.Movie_Community_Web.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public void validateDuplicateMemberId(String memberName) throws IllegalStateException {
        memberRepository.findByMemberName(memberName)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원");
                });
    }

    public int createMember(Member member) throws IllegalStateException {
        validateDuplicateMemberId(member.getMemberName());// 한번 더 중복체크

        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole("ROLE_USER");
        memberRepository.save(member);
        return 1;
    }

    public JSONObject selectMember(String memberName) throws NoSuchElementException {
        JSONObject member = new JSONObject();

        memberRepository.findByMemberName(memberName)
                .ifPresentOrElse(m -> {
                            member.put("memberName", m.getMemberName());
                            member.put("name", m.getName());
                            member.put("email", m.getEmail());
                            member.put("address", m.getAddress());
                            member.put("gender", m.getGender());
                            member.put("birth", m.getBirth());
                            member.put("phone", m.getPhone());
                        },
                        () -> {
                            throw new NoSuchElementException("존재하지 않는 회원에 대한 정보조회 요청");
                        });

        return member;
    }

    public void updateMember(Member member) throws NoSuchElementException {
        memberRepository.findByMemberName(member.getMemberName())
                .ifPresentOrElse(m -> {
                    m.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
                    m.setName(member.getName());
                    m.setEmail(member.getEmail());
                    m.setAddress(member.getAddress());
                    m.setGender(member.getGender());
                    m.setPhone(member.getPhone());
                }, () -> {
                    throw new NoSuchElementException("존재하지 않는 회원에 대한 정보수정 요청");
                });
    }

    public void deleteMember(String memberName) {
        memberRepository.findByMemberName(memberName)
                .ifPresentOrElse(m -> {
                            memberRepository.delete(m);
                        },
                        () -> {
                            throw new NoSuchElementException("존재하지 않는 회원에 대한 정보삭제 요청");
                        });
    }
}
