package study.board2.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "questions")
@Entity
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title; // 질문 제목

    @Column(name = "content", nullable = false)
    private String content; // 질문 내용

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    private Question(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public static Question of(String title, String content, Member member) {
        return new Question(title, content, member);
    }

    public static Question of(String title, String content) {
        return Question.of(title,content,null);
    }
}
