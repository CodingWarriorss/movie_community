package com.codeworrisors.Movie_Community_Web.repository;

import java.util.Optional;

import com.codeworrisors.Movie_Community_Web.model.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>{
    
    Optional<Member> findByEmail(String email);

    Boolean existsByEmail(String email);
}
