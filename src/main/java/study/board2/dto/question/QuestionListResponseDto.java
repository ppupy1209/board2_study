package study.board2.dto.question;

import lombok.Getter;
import study.board2.domain.Question;

import java.util.List;


@Getter
public class QuestionListResponseDto {

    private String writer;
    private String title;
    private List<String> tags;
    private Integer answerCnt;

    public QuestionListResponseDto(String writer, String title, List<String> tags, Integer answerCnt) {
        this.writer = writer;
        this.title = title;
        this.tags = tags;
        this.answerCnt = answerCnt;
    }

    public static QuestionListResponseDto of(Question question, List<String> tags) {
        return new QuestionListResponseDto(
                question.getMember().getName(),
                question.getTitle(),
                tags,
                question.getAnswers().size()
        );
    }


}
