package com.SurveyManager.BACKEND.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnswerRequestDTO {
    @NotBlank(message = "Answer text is required")
    private String text;

    private Integer orderIndex;

    private Boolean incrementCount;
} 