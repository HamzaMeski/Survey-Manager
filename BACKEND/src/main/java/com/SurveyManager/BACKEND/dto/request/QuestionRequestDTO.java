package com.SurveyManager.BACKEND.dto.request;

import com.SurveyManager.BACKEND.util.constants.QuestionType;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class QuestionRequestDTO {
    @NotBlank(message = "Question text is required")
    private String text;
    
    @NotNull(message = "Question type is required")
    private QuestionType type;
    
    private Boolean required = false;
    private Integer orderIndex;
    private String instructions;
} 