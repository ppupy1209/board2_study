package study.board2.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Setter
    @Column(nullable = false, unique = true)
    private String name; // 멤버 닉네임

    private Member(String name) {
        this.name = name;
    }

    public static Member of(String name) {
        return new Member(name);
    }

    private Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Member of(Long id, String name) {
        return new Member(id,name);
    }
}
