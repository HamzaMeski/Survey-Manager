package com.SurveyManager.BACKEND.dto.response;

import lombok.Data;

@Data
public class AnswerResponseDTO {
    private Long id;
    private String text;
    private Integer selectionCount;
    private Integer orderIndex;
} 