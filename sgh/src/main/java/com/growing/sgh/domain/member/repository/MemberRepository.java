package com.growing.sgh.domain.member.repository;

import com.growing.sgh.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
