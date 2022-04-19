package com.growing.sgh.domain.member.repository;

import com.growing.sgh.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Member findByUsername(String username);

    public boolean existsByUsername(String username);
    public boolean existsByNickname(String nickname);

}


