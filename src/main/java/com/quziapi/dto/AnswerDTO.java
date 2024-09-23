package com.quziapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerDTO {

    private Map<String, String> answers;
    private Map<String, Boolean> correct_answers;

}
