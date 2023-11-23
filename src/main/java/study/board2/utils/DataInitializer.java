package study.board2.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.board2.domain.Member;
import study.board2.domain.Question;
import study.board2.domain.Tag;
import study.board2.repository.MemberRepository;
import study.board2.repository.tag.TagRepository;
import study.board2.service.QuestionService;

import javax.annotation.PostConstruct;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DataInitializer {

    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;

    private final QuestionService questionService;

    @PostConstruct
    public void init() {
        Member member1 = Member.of("kim");
        Member member2 = Member.of("lee");
        Member member3 = Member.of("park");
        Member member4 = Member.of("choi");
        Member member5 = Member.of("jung");

        memberRepository.saveAll(List.of(member1,member2,member3,member4,member5));


        Tag tag1 = Tag.of("spring");
        Tag tag2 = Tag.of("java");
        Tag tag3 = Tag.of("python");
        Tag tag4 = Tag.of("kotlin");
        Tag tag5 = Tag.of("c++");

        tagRepository.saveAll(List.of(tag1, tag2, tag3, tag4, tag5));

        questionService.saveQuestion(1L, Question.of("제목1","내용1"), List.of("java", "python"));
        questionService.saveQuestion(2L, Question.of("제목2","내용2"),List.of("newTestTag", "python"));
        questionService.saveQuestion(3L, Question.of("제목3","내용3"),List.of("spring"));
        questionService.saveQuestion(4L, Question.of("제목4","내용4"),List.of("java", "kotlin"));
        questionService.saveQuestion(5L, Question.of("제목5","내용5"),List.of("spring","c++"));
    }
}
