package study.board2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board2.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
