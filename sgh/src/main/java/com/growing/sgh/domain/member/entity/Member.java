package com.growing.sgh.domain.member.entity;

import com.growing.sgh.domain.member.dto.ChangeMemberInfoDto;
import com.growing.sgh.domain.member.dto.SignUpRequest;
import com.growing.sgh.exception.member.BadPasswordException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    public Member(String username, String password, String nickname, String email, String address,
                  String phoneNum, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
        this.authorities = roles;
    }

    public void changePassword(String oldPassword, String nwPassword, PasswordEncoder passwordEncoder){
        if(!matchPassword(oldPassword, passwordEncoder))
            throw new BadPasswordException();
        this.password = passwordEncoder.encode(nwPassword);
    }

    public boolean matchPassword(String oldPwd, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(oldPwd, this.password);
    }

    public void setPassword(String newPw){
        if(newPw.isEmpty()) throw new IllegalArgumentException(("no new password"));
        this.password = newPw;
    }

    public void changeInfo(ChangeMemberInfoDto memberInfo){
        this.nickname = memberInfo.getNickname();
        this.address = memberInfo.getAddress();
        this.phoneNum = memberInfo.getPhoneNum();
    }

    public void addAuth(Role role){
        this.authorities.add(role);
    }

}
