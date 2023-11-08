package study.board2.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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


}
