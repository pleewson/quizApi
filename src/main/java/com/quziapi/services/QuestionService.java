package com.quziapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quziapi.dto.QuestionDTO;
import com.quziapi.entity.Question;
import com.quziapi.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper;


    public QuestionService(QuestionRepository questionRepository, ObjectMapper objectMapper) {
        this.questionRepository = questionRepository;
        this.objectMapper = objectMapper;
    }


    public QuestionDTO[] getQuestionsFromJson(String responseJSON) throws JsonProcessingException {
        return objectMapper.readValue(responseJSON, QuestionDTO[].class);
    }


    public Question getRandomQuestion() {
        List<Question> questions = questionRepository.findAll();
        Random rnd = new Random();

        return questions.get(rnd.nextInt(questions.size()));
    }

    public void saveQuestionToDB(Question question) {
        questionRepository.save(question);
    }


    public boolean checkIfQuestionExist(Long questionId){
        return questionRepository.findById(questionId).isPresent();
    }

}
