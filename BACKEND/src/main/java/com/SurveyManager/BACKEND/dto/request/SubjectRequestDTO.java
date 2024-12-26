package com.SurveyManager.BACKEND.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubjectRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    private Long parentSubjectId;
} 