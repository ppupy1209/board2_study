package study.board2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board2.domain.Member;
import study.board2.domain.Question;
import study.board2.domain.QuestionTag;
import study.board2.domain.Tag;
import study.board2.repository.QuestionRepository;
import study.board2.repository.QuestionTagRepository;
import study.board2.repository.TagRepository;

@RequiredArgsConstructor
@Transactional
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final MemberService memberService;

    private final TagService tagService;
    private final QuestionTagRepository questionTagRepository;

    public Long saveQuestion(Long memberId, Question question,String tags) {
        Member member = memberService.findVerifiedMember(memberId);

        question.setMember(member);

        Question savedQuestion = questionRepository.save(question);

        String[] splitTag = tags.split(",");
        for (String tagName : splitTag) {
            Tag tag = tagService.findVerifiedTag(tagName);
            questionTagRepository.save(QuestionTag.of(tag,savedQuestion));
        }

        return savedQuestion.getId();
    }
}
