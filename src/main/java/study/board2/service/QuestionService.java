package study.board2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board2.domain.Member;
import study.board2.domain.Question;
import study.board2.domain.QuestionTag;
import study.board2.domain.Tag;
import study.board2.dto.question.QuestionResponseDto;
import study.board2.exception.BusinessLogicException;
import study.board2.exception.ExceptionCode;
import study.board2.repository.question.QuestionRepository;
import study.board2.repository.question.QuestionTagRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public QuestionResponseDto findQuestion(Long questionId) {
        Question question = findVerifiedQuestion(questionId);

        List<QuestionTag> questionTags = questionTagRepository.findByQuestionId(questionId);
        List<String> tags = questionTags.stream()
                .map(questionTag -> questionTag.getTag().getName())
                .collect(Collectors.toList());

        return QuestionResponseDto.of(question,tags);
    }

    private Question findVerifiedQuestion(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        Question question = optionalQuestion.orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        return question;
    }


}
