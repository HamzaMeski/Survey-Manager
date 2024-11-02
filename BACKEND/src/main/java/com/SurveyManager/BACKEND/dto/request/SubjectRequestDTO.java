package com.SurveyManager.BACKEND.dto.request;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class SubjectRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    private Long parentSubjectId;
    
    @NotNull(message = "Level is required")
    private Integer level;
    
    @NotBlank(message = "Path is required")
    private String path;
} 