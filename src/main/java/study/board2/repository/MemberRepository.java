package study.board2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board2.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);
}
