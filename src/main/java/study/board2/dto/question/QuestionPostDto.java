package study.board2.dto.question;

import lombok.Getter;
import study.board2.domain.Question;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class QuestionPostDto {

    private Long memberId;
    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    private List<String> tags;

    public Question toEntity() {
        return Question.of(title,content);
    }
}
