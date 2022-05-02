package com.growing.sgh.domain.member.dto;

import com.growing.sgh.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class MemberInfoDto {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String address;
    private String phoneNum;

    @Builder
    public MemberInfoDto(Long id, String username, String nickname, String email, String address, String phoneNum) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public static MemberInfoDto of (Member member){
        return MemberInfoDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .address(member.getAddress())
                .phoneNum(member.getPhoneNum())
                .build();
    }
}
