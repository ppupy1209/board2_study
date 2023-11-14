package study.board2.repository.question;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board2.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositoryCustom {
}
