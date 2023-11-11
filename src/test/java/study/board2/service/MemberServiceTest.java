package study.board2.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import study.board2.domain.Member;
import study.board2.dto.member.MemberResponseDto;
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
        MemberResponseDto response = memberService.findMember(expectedMember.getId());

        // then
        assertThat(response.getName()).isEqualTo(expectedName);
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
        Page<MemberResponseDto> pagedMembers = memberService.findMembers(0, 2);
        List<MemberResponseDto> members = pagedMembers.getContent();

        // then
        assertThat(members).hasSize(2)
                .extracting("id", "name")
                .containsExactlyInAnyOrder(
                        tuple(1L, "kim"),
                        tuple(2L, "lee")
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


    @DisplayName("멤버 이름이 중복인 경우 ALREADY_MEMBER_NAME_EXISTS 예외 발생")
    @Test
    void checkDuplicateName() {
        // given
        String givenName = "testName";
        memberRepository.save(Member.of(givenName));

        // when & then
        assertThatThrownBy(() -> memberService.saveMember(Member.of(givenName)))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("Already Member_Name Exists");

    }


}