package com.growing.sgh.domain.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneNum {
    private String PhoneNum1;
    private String PhoneNum2;
    private String PhoneNum3;

}
