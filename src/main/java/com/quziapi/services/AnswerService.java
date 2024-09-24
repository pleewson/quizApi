package com.quziapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quziapi.dto.AnswerDTO;
import com.quziapi.dto.AnswerValidatorDTO;
import com.quziapi.entity.Answer;
import com.quziapi.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final ObjectMapper objectMapper;


    public AnswerService(AnswerRepository answerRepository, ObjectMapper objectMapper) {
        this.answerRepository = answerRepository;
        this.objectMapper = objectMapper;
    }

    public AnswerDTO[] getAnswersFromJson(String responseJSON) throws JsonProcessingException {
        return objectMapper.readValue(responseJSON, AnswerDTO[].class);
    }


    public boolean validateIfAnswersAreCorrect(AnswerValidatorDTO answerValidator) {
        List<Answer> answers = getQuestionAnswers(answerValidator.getQuestionId());
        int correctAnswersInQuestion = 0;
        for (Answer answer : answers) {
            if (answer.isCorrect()) {
                correctAnswersInQuestion++;
            }
        }

        int correctAnswersFromUser = 0;
        for (Long answerId : answerValidator.getAnswers()) {
            if (!checkIfAnswerIsCorrect(answerId)) {
                return false;
            }
            correctAnswersFromUser++;
        }

        return correctAnswersInQuestion == correctAnswersFromUser;
    }


    public List<Answer> getQuestionAnswers(Long questionId) {
        return answerRepository.findAllByQuestionId(questionId);
    }


    public boolean checkIfAnswerIsCorrect(Long answerId) {
        Answer answer = answerRepository.findAnswerById(answerId);
        return answer.isCorrect();
    }


    public void saveAnswerToDB(Answer answer){
        answerRepository.save(answer);
    }
}
