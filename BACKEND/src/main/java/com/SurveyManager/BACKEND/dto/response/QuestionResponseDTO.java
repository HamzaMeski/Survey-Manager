package com.SurveyManager.BACKEND.dto.response;

import com.SurveyManager.BACKEND.util.constants.QuestionType;
import lombok.Data;
import java.util.List;

@Data
public class QuestionResponseDTO {
    private Long id;
    private String text;
    private Integer answerCount;
    private QuestionType type;
    private Boolean required;
    private Integer orderIndex;
    private String instructions;
    private List<AnswerResponseDTO> answers;
} 