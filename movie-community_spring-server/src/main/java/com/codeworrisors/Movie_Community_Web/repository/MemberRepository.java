package com.codeworrisors.Movie_Community_Web.repository;

import com.codeworrisors.Movie_Community_Web.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberName(String memberName);

    Boolean existsByMemberName( String memberName);
}
