package study.board2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board2.domain.Member;
import study.board2.exception.BusinessLogicException;
import study.board2.exception.ExceptionCode;
import study.board2.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;


    public Long saveMember(Member member) {

        Member savedMember = memberRepository.save(member);

        return savedMember.getId();
    }


    public Member updateMember(Member member, Long memberId) {
        Member verifiedMember = findVerifiedMember(memberId);

        Optional.ofNullable(member.getName())
                .ifPresent(name -> verifiedMember.setName(name));

        return verifiedMember;
    }

    public Member findMember(Long memberId) {
        return findVerifiedMember(memberId);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public void deleteMember(Long memberId) {
        Member member = findVerifiedMember(memberId);

        memberRepository.delete(member);
    }

    private Member findVerifiedMember(Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        Member member = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return member;
    }



}
