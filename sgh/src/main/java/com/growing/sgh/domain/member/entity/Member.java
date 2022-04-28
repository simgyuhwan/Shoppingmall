package com.growing.sgh.domain.member.entity;

import com.growing.sgh.domain.member.dto.ChangeMemberInfoDto;
import com.growing.sgh.domain.member.dto.SignUpRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(name = "nickname",nullable = false, unique = true)
    private String nickname;

    @Column(name = "address")
    private String address;

    @Column(name = "phoneNum")
    private String phoneNum;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Collection<Role> authorities = new ArrayList<>();

    @Builder
    public Member(String username, String password, String nickname, String email, String address,String phoneNum) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public void changePassword(String nwPassword, PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(nwPassword);
    }

    public void changeInfo(ChangeMemberInfoDto memberInfo){
        this.nickname = memberInfo.getNickname();
        this.address = memberInfo.getNickname();
        this.phoneNum = memberInfo.getPhoneNum();
    }

    public void addAuth(Role role){
        this.authorities.add(role);
    }

}
