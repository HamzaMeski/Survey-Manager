package com.SurveyManager.BACKEND.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class AnswerRequestDTO {
    @NotBlank(message = "Answer text is required")
    private String text;
    
    private Integer orderIndex;
} 