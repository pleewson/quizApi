package com.quziapi.controller;

import com.quziapi.services.QuestionAnswerService;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api")
public class QuestionController {
    private final QuestionAnswerService questionAnswerService;

    public QuestionController(QuestionAnswerService questionAnswerService) {
        this.questionAnswerService = questionAnswerService;
    }

    @GetMapping("/questions")
    @ResponseBody
    public String getQuestions() {
        return questionAnswerService.responseAllQuestionsAndAnswersFromDB();
    }

}
