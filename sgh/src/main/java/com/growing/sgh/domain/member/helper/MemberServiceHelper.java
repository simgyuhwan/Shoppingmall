package com.growing.sgh.domain.member.helper;

import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.domain.member.repository.MemberRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;

public final class MemberServiceHelper {

    public static Member findExistingMember(MemberRepository repository, Long memberId){
        return repository.findById(memberId).orElseThrow(EntityNotFoundException::new);
    }

}
