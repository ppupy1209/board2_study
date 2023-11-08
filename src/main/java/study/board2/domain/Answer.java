package study.board2.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "answers")
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content; // 답변 내용

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Answer parent; // 부모 답변

    @OneToMany(mappedBy = "parent",cascade = CascadeType.ALL)
    private List<Answer> children = new ArrayList<>(); // 자식 답변

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
