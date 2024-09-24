package com.quziapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerValidatorDTO {
    Long questionId;
    Long[] answers;
}
