package com.growing.sgh.domain.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Role {

    @Id @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberNo;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public Role(Long memberNo, RoleType roleType){
        this.memberNo = memberNo;
        this.roleType = roleType;
    }


}
