package study.board2.dto.question;

import lombok.Getter;
import study.board2.domain.Question;


@Getter
public class QuestionListResponseDto {

    private String writer;
    private String title;
    private Integer answerCnt;

    public QuestionListResponseDto(String writer, String title, Integer answerCnt) {
        this.writer = writer;
        this.title = title;
        this.answerCnt = answerCnt;
    }

    public static QuestionListResponseDto of(Question question) {
        return new QuestionListResponseDto(
                question.getMember().getName(),
                question.getTitle(),
                question.getAnswers().size()
        );
    }
}
