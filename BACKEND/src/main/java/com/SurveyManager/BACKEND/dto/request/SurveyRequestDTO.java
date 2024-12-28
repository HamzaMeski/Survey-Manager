package com.SurveyManager.BACKEND.dto.request;

import com.SurveyManager.BACKEND.util.constants.SurveyStatus;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class SurveyRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Title is required")
    private String description;
    
    @NotNull(message = "Status is required")
    private SurveyStatus status;
    
    private String category;
    
    private String targetAudience;
    
    @NotNull(message = "Owner ID is required")
    private Long ownerId;
} 