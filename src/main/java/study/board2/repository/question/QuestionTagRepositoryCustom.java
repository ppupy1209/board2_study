package study.board2.repository.question;

import study.board2.domain.QuestionTag;

import java.util.List;

public interface QuestionTagRepositoryCustom {
    List<QuestionTag> findByQuestionId(Long questionId);
}
