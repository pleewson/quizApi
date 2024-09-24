package com.quziapi.services;

import com.quziapi.dto.AnswerDTO;
import com.quziapi.dto.AnswerValidatorDTO;
import com.quziapi.dto.QuestionDTO;
import com.quziapi.entity.Answer;
import com.quziapi.entity.Question;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class QuestionAnswerManagementService {
    private final QuestionService questionService;
    private final AnswerService answerService;

    public QuestionAnswerManagementService(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }


    public void saveQuestionAndAnswersToDB(QuestionDTO[] questionsDTO, AnswerDTO[] answersDTO) {

        for (int i = 0; i < questionsDTO.length; i++) {
            Question question = new Question();
            question.setQuestion(questionsDTO[i].getQuestion());
            question.setApiId(questionsDTO[i].getId());

            questionService.saveQuestionToDB(question);

            Iterator<Map.Entry<String, String>> answersIterator = answersDTO[i].getAnswers().entrySet().iterator();
            Iterator<Map.Entry<String, Boolean>> correctAnswerIterator = answersDTO[i].getCorrect_answers().entrySet().iterator();

            while (answersIterator.hasNext() && correctAnswerIterator.hasNext()) {
                String answerStr = answersIterator.next().getValue();
                if (answerStr != null) {
                    Answer answer = new Answer();
                    answer.setQuestion(question);
                    answer.setAnswer(answerStr);
                    answer.setCorrect(correctAnswerIterator.next().getValue());

                    answerService.saveAnswerToDB(answer);
                }
            }
        }
    }


    public String getRandomQuestionWithAnswersAsJson() {
        Question randomQuestion = questionService.getRandomQuestion();

        JSONObject outputJSON = new JSONObject();

        outputJSON.put("id", randomQuestion.getId());
        outputJSON.put("question", randomQuestion.getQuestion());

        List<Answer> answers = answerService.getQuestionAnswers(randomQuestion.getId());
        JSONArray jsonAnswersArray = new JSONArray();
        for (Answer answer : answers) {
            JSONObject jsonAnswer = new JSONObject();
            jsonAnswer.put("id", answer.getId());
            jsonAnswer.put("answer", answer.getAnswer());
            jsonAnswersArray.put(jsonAnswer);
        }
        outputJSON.put("answers", jsonAnswersArray);

        return outputJSON.toString();
    }


    public String getAnswerValidationAsJson(AnswerValidatorDTO answerValidator) {
        JSONObject json = new JSONObject();
        json.put("correct", answerService.validateIfAnswersAreCorrect(answerValidator));
        return json.toString();
    }
}
