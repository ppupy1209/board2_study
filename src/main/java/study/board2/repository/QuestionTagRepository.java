package study.board2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board2.domain.QuestionTag;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long> {
}
