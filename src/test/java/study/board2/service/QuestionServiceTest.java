package study.board2.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.board2.domain.Member;
import study.board2.domain.Question;
import study.board2.repository.MemberRepository;
import study.board2.repository.question.QuestionRepository;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class QuestionServiceTest {


    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private MemberRepository memberRepository;


//    @DisplayName("질문을 등록한다.")
//    @Test
//    void saveQuestion() {
//        // given
//        Member member = memberRepository.save(Member.of("testName"));
//        Question request = Question.of("제목","내용");
//        String tags = "spring,java";
//
//        // when
//        Long questionId = questionService.saveQuestion(member.getId(), request, tags);
//
//        // then
//        assertThat(questionId).isNotNull();
//    }
}