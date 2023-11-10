package study.board2.dto.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.board2.domain.Member;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class MemberPostDto {
    @NotBlank(message = "이름은 필수 항목입니다.")
    private String name;

    public static MemberPostDto of(String name) {
        return new MemberPostDto(name);
    }

    public Member toEntity() {
        return Member.of(name);
    }
}
