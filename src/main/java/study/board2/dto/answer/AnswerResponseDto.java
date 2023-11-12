package study.board2.dto.answer;

import study.board2.domain.Answer;

public class AnswerResponseDto {

    private String writer;

    private String content;


    private AnswerResponseDto(String writer, String content) {
        this.writer = writer;
        this.content = content;
    }

    public static AnswerResponseDto of(Answer answer) {
        return new AnswerResponseDto(answer.getMember().getName(), answer.getContent());
    }
}
