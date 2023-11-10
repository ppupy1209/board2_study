package study.board2.dto.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import study.board2.domain.Member;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberResponseDto {

    private Long id;
    private String name;


    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getId(), member.getName());
    }
}
