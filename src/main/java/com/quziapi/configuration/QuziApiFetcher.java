package com.quziapi.configuration;

import com.quziapi.dto.AnswerDTO;
import com.quziapi.dto.QuestionDTO;
import com.quziapi.services.AnswerService;
import com.quziapi.services.QuestionAnswerManagementService;
import com.quziapi.services.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class QuziApiFetcher implements CommandLineRunner {
    private final RestTemplate restTemplate;
    private final QuestionAnswerManagementService questionAnswerService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public QuziApiFetcher(RestTemplate restTemplate, QuestionAnswerManagementService questionAnswerService, QuestionService questionService, AnswerService answerService) {
        this.restTemplate = restTemplate;
        this.questionAnswerService = questionAnswerService;
        this.questionService = questionService;
        this.answerService = answerService;
    }


    @Override
    public void run(String... args) throws Exception {
        String apiUrl = "https://quizapi.io/api/v1/questions?apiKey=WQAxegE2AtfEPeWkMnOPpOzrqFxTTagJxy04cZ4Z&limit=10"; //there you can change amount fetching questions
        String responseJSON = restTemplate.getForObject(apiUrl, String.class);

        QuestionDTO[] questionDTO = questionService.getQuestionsFromJson(responseJSON);
        AnswerDTO[] answerDTO = answerService.getAnswersFromJson(responseJSON);

        log.info("questionDTO[0]: id: {}, question: {}", questionDTO[0].getId(), questionDTO[0].getQuestion());
        log.info("answersDTO answers: {}", answerDTO[0].getAnswers());
        log.info("answersDTO correct_answers: {}", answerDTO[0].getCorrect_answers());

        questionAnswerService.saveQuestionAndAnswersToDB(questionDTO, answerDTO);
    }
}
