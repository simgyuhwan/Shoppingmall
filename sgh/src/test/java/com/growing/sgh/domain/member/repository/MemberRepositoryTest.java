package com.growing.sgh.domain.member.repository;

import com.growing.sgh.domain.member.dto.ChangeMemberInfoDto;
import com.growing.sgh.domain.member.entity.Member;
import com.growing.sgh.exception.member.MemberNotFoundException;
import com.growing.sgh.exception.member.NicknameAlreadyExistsException;
import com.growing.sgh.factory.entity.MemberFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("회원 생성 및 조회 테스트")
    void createAndReadTest(){
        // given
        Member member = createMember();

        //when
        memberRepository.save(member);
        clear();

        //then
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        assertThat(findMember.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("회원 Date 테스트")
    void validateMemberInfoTest(){
        // given
        Member member = createMember();

        //when
        memberRepository.save(member);
        clear();

        //then
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        assertThat(findMember.getRegTime()).isNotNull();
        assertThat(findMember.getUpdateTime()).isNotNull();
        assertThat(findMember.getRegTime()).isEqualTo(findMember.getUpdateTime());

    }

    @Test
    @DisplayName("MemberNotFoundException 테스트")
    void MemberNotFoundExceptionTest(){
        // given
        Member member = createMember();

        //when
        memberRepository.save(member);
        clear();

        //then
        assertThrows(MemberNotFoundException.class, ()->{
            memberRepository.findById(member.getId()+1).orElseThrow(MemberNotFoundException::new);
        });
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void MemberDeleteTest(){
        // given
        Member member = memberRepository.save(createMember());
        clear();

        //when
        memberRepository.delete(member);
        clear();

        //then
        assertThatThrownBy(()-> memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("changePassword 테스트")
    void changePasswordTest(){
        // given
        Member member = memberRepository.save(securityMember());
        clear();

        //when
        memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new)
                .changePassword("1111", "1234", passwordEncoder());
        clear();

        //then
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
        assertThat(findMember.matchPassword("1234", passwordEncoder())).isTrue();
    }

    @Test
    @DisplayName("matchPassword 테스트")
    void matchPasswordTest(){
        // given
        Member member = memberRepository.save(securityMember());
        clear();

        //when
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);

        //then
        assertThat(findMember.matchPassword("1111", passwordEncoder())).isTrue();
    }

    @Test
    @DisplayName("회원 정보 수정 테스트")
    void changeInfoTest(){
        // given
        ChangeMemberInfoDto changeInfo = ChangeMemberInfoDto.builder().nickname("test2")
                .phoneNum("010-1111-1234")
                .address("서울시 마포구").build();
        Member member = memberRepository.save(createMember());
        member.changeInfo(changeInfo);
        clear();

        //when
        Member findMember = memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);

        //then
        assertThat(findMember.getNickname()).isEqualTo("test2");
        assertThat(findMember.getPhoneNum()).isEqualTo("010-1111-1234");
        assertThat(findMember.getAddress()).isEqualTo("서울시 마포구");
    }

    @Test
    @DisplayName("findByUsername 테스트")
    void findByUsernameTest(){
        // given
        Member member = memberRepository.save(createMember());
        clear();

        //when
        Member findMember = memberRepository.findByUsername(member.getUsername()).orElseThrow(MemberNotFoundException::new);

        //then
        assertThat(member.getUsername()).isEqualTo(findMember.getUsername());
    }

    @Test
    @DisplayName("existsByUsername 테스트")
    void existsByUsernameTest(){
        // given
        Member member = memberRepository.save(createMember());
        clear();

        //when, then
        assertThat(memberRepository.existsByUsername(member.getUsername())).isTrue();
    }

    @Test
    @DisplayName("existsByNickname 테스트")
    void existsByNicknameTest(){
        // given
        Member member = memberRepository.save(createMember());
        clear();

        //when,then
        assertThat(memberRepository.existsByNickname(member.getNickname())).isTrue();
    }

    @Test
    @DisplayName("existsByEmail 테스트")
    void existsEmailTest(){
        // given
        Member member = memberRepository.save(createMember());
        clear();

        // when, then
        assertThat(memberRepository.existsByEmail(member.getEmail()));
    }

    @Test
    @DisplayName("nickname unique 테스트")
    void uniqueNicknameTest(){
        //given
        Member member = memberRepository.save(createMember("test1", "1111", "test@test.com", "test"));
        clear();
        //when, then
        assertThatThrownBy(() -> memberRepository.save(createMember("test2", "11111", "test2@test.com", member.getNickname())))
               .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("username unique 테스트트")
    void uniqueUsernameTest(){
        //given
        Member member = memberRepository.save(createMember("test1", "1111", "test@test.com", "test1"));
        clear();
        //when, then
        assertThatThrownBy(() -> memberRepository.save(createMember(member.getUsername(), "11111", "test2@test.com", "test2")))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
    private void clear(){
        em.flush();
        em.clear();
    }

    private Member createMember(){
        return MemberFactory.createMember();
    }

    private Member createMember(String username, String password, String email, String nickname){
        return MemberFactory.createMember(username, password, email, nickname);
    }
    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private Member securityMember(){
        return MemberFactory.passwordEncodeMember(passwordEncoder());
    }

}