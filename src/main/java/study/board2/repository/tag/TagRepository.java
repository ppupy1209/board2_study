package study.board2.repository.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board2.domain.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String tag);
}
