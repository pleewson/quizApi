package com.quziapi.configuration;

import com.quziapi.dto.AnswerDTO;
import com.quziapi.dto.QuestionDTO;
import com.quziapi.services.QuestionAnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class QuziApiFetcher implements CommandLineRunner {
    private final RestTemplate restTemplate;
    private final QuestionAnswerService questionAnswerService;

    public QuziApiFetcher(RestTemplate restTemplate, QuestionAnswerService questionAnswerService) {
        this.restTemplate = restTemplate;
        this.questionAnswerService = questionAnswerService;
    }


    @Override
    public void run(String... args) throws Exception {
        String apiUrl = "https://quizapi.io/api/v1/questions?apiKey=WQAxegE2AtfEPeWkMnOPpOzrqFxTTagJxy04cZ4Z&limit=10";
        String responseJSON = restTemplate.getForObject(apiUrl, String.class);

        QuestionDTO[] questionDTO = questionAnswerService.getQuestionsFromJson(responseJSON);
        AnswerDTO[] answerDTO = questionAnswerService.getAnswersFromJson(responseJSON);

        log.info("questionDTO[0]: id: {}, question: {}", questionDTO[0].getId(), questionDTO[0].getQuestion());
        log.info("answersDTO answers: {}", answerDTO[0].getAnswers());
        log.info("answersDTO correct_answers: {}", answerDTO[0].getCorrect_answers());

        log.info("questionDTO[0]: id: {}, question: {}", questionDTO[9].getId(), questionDTO[9].getQuestion());
        log.info("answersDTO answers: {}", answerDTO[9].getAnswers());
        log.info("answersDTO correct_answers: {}", answerDTO[9].getCorrect_answers());

        questionAnswerService.saveQuestionAndAnswerToDB(questionDTO,answerDTO);

    }

}
