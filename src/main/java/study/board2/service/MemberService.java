package study.board2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board2.domain.Member;
import study.board2.dto.member.MemberResponseDto;
import study.board2.exception.BusinessLogicException;
import study.board2.exception.ExceptionCode;
import study.board2.repository.MemberRepository;


import java.util.Optional;


@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;


    public Long saveMember(Member member) {
        checkDuplicateName(member.getName());

        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }

    private void checkDuplicateName(String name) {
        Optional<Member> member = memberRepository.findByName(name);

        if (member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.ALREADY_MEMBER_NAME_EXISTS);
        }
    }


    public Member updateMember(Member member, Long memberId) {
        Member verifiedMember = findVerifiedMember(memberId);

        Optional.ofNullable(member.getName())
                .ifPresent(name -> verifiedMember.setName(name));

        return verifiedMember;
    }

    public MemberResponseDto findMember(Long memberId) {
        Member member = findVerifiedMember(memberId);

        return MemberResponseDto.of(member);
    }

    public Page<MemberResponseDto> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page,size)).map(MemberResponseDto::of);
    }

    public void deleteMember(Long memberId) {
        Member member = findVerifiedMember(memberId);

        memberRepository.delete(member);
    }

    public Member findVerifiedMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        Member member = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return member;
    }



}
