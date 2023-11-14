package study.board2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.board2.dto.question.QuestionPostDto;
import study.board2.dto.question.QuestionResponseDto;
import study.board2.dto.response.SingleResponseDto;
import study.board2.service.QuestionService;
import study.board2.utils.UriCreator;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/questions")
@RestController
public class QuestionController {

    private final static String QUESTION_DEFAULT_URL = "/questions";
    private final QuestionService questionService;


    @PostMapping
    public ResponseEntity postQuestion(@Valid @RequestBody QuestionPostDto request) {
        Long questionId = questionService.saveQuestion(request.getMemberId(), request.toEntity(), request.getTags());

        URI location = UriCreator.createUri(QUESTION_DEFAULT_URL, questionId);

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{question-id}")
    public ResponseEntity getQuestion(@PathVariable("question-id") Long questionId) {
        QuestionResponseDto response = questionService.findQuestion(questionId);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getQuestions(@RequestParam int page,
                                       @RequestParam int size) {
        return new ResponseEntity<>(questionService.findQuestions(page-1,size),HttpStatus.OK);
    }
}
