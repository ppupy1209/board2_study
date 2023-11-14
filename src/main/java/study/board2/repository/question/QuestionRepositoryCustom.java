package study.board2.repository.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.board2.domain.Question;

public interface QuestionRepositoryCustom {

    Page<Question> findQuestionsWithMember(Pageable pageable);
}
