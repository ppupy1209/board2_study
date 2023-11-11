package study.board2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.board2.dto.question.QuestionPostDto;
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
}
