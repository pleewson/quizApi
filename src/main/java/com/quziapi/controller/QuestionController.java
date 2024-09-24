package com.quziapi.controller;

import com.quziapi.dto.AnswerValidatorDTO;
import com.quziapi.services.QuestionAnswerManagementService;
import com.quziapi.services.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class QuestionController {
    private final QuestionAnswerManagementService questionAnswerService;
    private final QuestionService questionService;

    public QuestionController(QuestionAnswerManagementService questionAnswerService, QuestionService questionService) {
        this.questionAnswerService = questionAnswerService;
        this.questionService = questionService;
    }

    @GetMapping("/api/questions")
    public String getQuestions() {
        return questionAnswerService.getRandomQuestionWithAnswersAsJson();
    }


    @PostMapping("/api/answers")
    public ResponseEntity<String> getAnswers(@RequestBody AnswerValidatorDTO answerValidator) {

        log.info("JSON from JS id: {}, answers: {}", answerValidator.getQuestionId(), answerValidator.getAnswers());

        if(!questionService.checkIfQuestionExist(answerValidator.getQuestionId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question doesn't exist");
        }

        String isCorrectJSON = questionAnswerService.getAnswerValidationAsJson(answerValidator);
        log.info("from JAVA to JS: {} ", isCorrectJSON);

        return ResponseEntity.ok(isCorrectJSON);
    }
}
