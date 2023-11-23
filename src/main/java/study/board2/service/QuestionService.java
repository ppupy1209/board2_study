package study.board2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board2.domain.*;
import study.board2.dto.question.QuestionListResponseDto;
import study.board2.dto.question.QuestionResponseDto;
import study.board2.dto.response.MultiResponseDto;
import study.board2.exception.BusinessLogicException;
import study.board2.exception.ExceptionCode;
import study.board2.repository.question.QuestionRepository;
import study.board2.repository.question.QuestionTagRepository;

import java.util.ArrayList;
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

    public Long saveQuestion(Long memberId, Question question, String tags) {
        Member member = memberService.findVerifiedMember(memberId);

        question.setMember(member);

        Question savedQuestion = questionRepository.save(question);

        String[] splitTag = tags.split(",");
        for (String tagName : splitTag) {
            Tag tag = tagService.findVerifiedTag(tagName);
            questionTagRepository.save(QuestionTag.of(tag, savedQuestion));
        }

        return savedQuestion.getId();
    }

    public QuestionResponseDto findQuestion(Long questionId) {
        Question question = findVerifiedQuestion(questionId);

        List<QuestionTag> questionTags = questionTagRepository.findByQuestionId(questionId);
        List<String> tags = questionTags.stream()
                .map(questionTag -> questionTag.getTag().getName())
                .collect(Collectors.toList());

        return QuestionResponseDto.of(question, tags);
    }

    public MultiResponseDto findQuestions(int page, int size, SearchType searchType, String keyword) {

        Page<Question> questionPage = Page.empty();
        Pageable pageable = PageRequest.of(page, size);
        switch (searchType) {
            case TITLE:
                questionPage = questionRepository.findByTitleContaining(keyword, pageable);
                break;
            default:
                questionPage = questionRepository.findQuestionsWithMember(pageable);
        }


        List<Question> questions = questionPage.getContent();

        List<QuestionListResponseDto> response = new ArrayList<>();

        for (Question question : questions) {
            List<QuestionTag> questionTags = questionTagRepository.findByQuestionId(question.getId());
            List<String> tags = questionTags.stream()
                    .map(questionTag -> questionTag.getTag().getName())
                    .collect(Collectors.toList());

            QuestionListResponseDto questionListResponseDto = QuestionListResponseDto.of(question, tags);

            response.add(questionListResponseDto);
        }

        return new MultiResponseDto<>(response, questionPage);
    }

    private Question findVerifiedQuestion(Long questionId) {
        Optional<Question> optionalQuestion = questionRepository.findById(questionId);

        Question question = optionalQuestion.orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        return question;
    }


}
