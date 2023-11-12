package study.board2.dto.question;

import lombok.Getter;
import study.board2.domain.Question;
import study.board2.dto.answer.AnswerResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class QuestionResponseDto {

    private String writer;
    private String title;
    private String content;

    private List<AnswerResponseDto> answers;

    private QuestionResponseDto(String writer, String title, String content, List<AnswerResponseDto> answers) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.answers = answers;
    }

    public static QuestionResponseDto of(Question question) {
        return new QuestionResponseDto(
                question.getMember().getName(),
                question.getTitle(),
                question.getContent(),
                question.getAnswers().stream()
                        .map(AnswerResponseDto::of)
                        .collect(Collectors.toList())
                );
    }
}
