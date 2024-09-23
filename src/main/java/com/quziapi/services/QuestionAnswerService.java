package com.quziapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quziapi.dto.AnswerDTO;
import com.quziapi.dto.QuestionDTO;
import com.quziapi.entity.Answer;
import com.quziapi.entity.Question;
import com.quziapi.repository.AnswerRepository;
import com.quziapi.repository.QuestionRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionAnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper;

    public QuestionAnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, ObjectMapper objectMapper) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.objectMapper = objectMapper;
    }

    public QuestionDTO[] getQuestionsFromJson(String responseJSON) throws JsonProcessingException {
        return objectMapper.readValue(responseJSON, QuestionDTO[].class);
    }

    public AnswerDTO[] getAnswersFromJson(String responseJSON) throws JsonProcessingException {
        return objectMapper.readValue(responseJSON, AnswerDTO[].class);
    }

    public void saveQuestionAndAnswerToDB(QuestionDTO[] questionsDTO, AnswerDTO[] answersDTO) {

        for (int i = 0; i < questionsDTO.length; i++) {
            Question question = new Question();
            question.setQuestion(questionsDTO[i].getQuestion());
            question.setApiId(questionsDTO[i].getId());

            questionRepository.save(question);

            Iterator<Map.Entry<String, String>> answersIterator = answersDTO[i].getAnswers().entrySet().iterator();
            Iterator<Map.Entry<String, Boolean>> correctAnswerIterator = answersDTO[i].getCorrect_answers().entrySet().iterator();

            while (answersIterator.hasNext() && correctAnswerIterator.hasNext()) {
                String answerStr = answersIterator.next().getValue();
                if (answerStr != null) {
                    Answer answer = new Answer();
                    answer.setQuestion(question);
                    answer.setAnswer(answerStr);
                    answer.setCorrect(correctAnswerIterator.next().getValue());

                    answerRepository.save(answer);
                }
            }

        }
    }


    private List<Answer> getQuestionAnswers(Long questionId) {
        return answerRepository.findAllByQuestionId(questionId);
    }


    public String responseAllQuestionsAndAnswersFromDB() {
        List<Question> questions = questionRepository.findAll();
        System.out.println("QUESTIONS SIZE : " + questions.size());
        JSONArray jsonArray = new JSONArray();

        for (Question question : questions) {
            JSONObject jsonQuestion = new JSONObject();
            jsonQuestion.put("id", question.getId());
            jsonQuestion.put("question", question.getQuestion());

            jsonArray.put(jsonQuestion);

            List<Answer> answers = getQuestionAnswers(question.getId());
            JSONArray jsonAnswersArray = new JSONArray();
            for (Answer answer : answers) {
                JSONObject jsonAnswer = new JSONObject();
                jsonAnswer.put("id", answer.getId());
                jsonAnswer.put("answer", answer.getAnswer());

                jsonAnswersArray.put(jsonAnswer);
                jsonArray.put(jsonAnswersArray);
            }


        }

        return jsonArray.toString();
    }


}
