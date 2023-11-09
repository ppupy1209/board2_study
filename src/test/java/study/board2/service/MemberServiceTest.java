package study.board2.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.board2.domain.Member;
import study.board2.exception.BusinessLogicException;
import study.board2.repository.MemberRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원을 등록한다.")
    @Test
    void saveMember() {
        // given
        Member member = Member.of("kim");

        // when
        Long memberId = memberService.saveMember(member);

        // then
        assertThat(memberId).isNotNull();
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void updateMember() {
        // given
        Member existingMember = Member.of("kim");
        Member savedMember = memberRepository.save(existingMember);

        String expectedName = "lee";
        Member expectedMember = Member.of(expectedName);

        // when
        Member actualMember = memberService.updateMember(expectedMember, savedMember.getId());

        // then
        assertThat(actualMember.getName()).isEqualTo(expectedName);
    }

    @DisplayName("회원 1명을 조회한다.")
    @Test
    void findMember() {
        // given
        String expectedName = "kim";
        Member member = Member.of(expectedName);
        Member expectedMember = memberRepository.save(member);

        // when
        Member actualMember = memberService.findMember(expectedMember.getId());

        // then
        assertThat(actualMember.getName()).isEqualTo(expectedName);
    }

    @DisplayName("회원 1명을 조회할 때 없는 회원일 경우 MEMBER_NOT_FOUND 예외를 던진다.")
    @Test
    void findMemberWithMemberNotFoundException() {

        // when & then
        assertThatThrownBy(() -> memberService.findMember(1L))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("Member Not Found");

    }

    @DisplayName("전체 회원을 조회한다.")
    @Test
    void findAllMembers() {
        // given
        Member member1 = Member.of("kim");
        Member member2 = Member.of("lee");

        memberRepository.saveAll(List.of(member1, member2));

        // when
        List<Member> members = memberService.findMembers();

        // then
        assertThat(members).hasSize(2)
                .extracting("name")
                .containsExactlyInAnyOrder(
                        "kim","lee"
                );
    }

    @DisplayName("회원을 삭제한다.")
    @Test
    void deleteMember() {
        // given
        Member member = memberRepository.save(Member.of("kim"));

        // when
        memberService.deleteMember(member.getId());
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members).isEmpty();
    }


}