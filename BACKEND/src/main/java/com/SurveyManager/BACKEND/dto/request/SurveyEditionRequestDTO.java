package com.SurveyManager.BACKEND.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SurveyEditionRequestDTO {
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;
    
    @NotNull(message = "End date is required")
    private LocalDateTime endDate;
    
    @NotNull(message = "Year is required")
    private Integer year;
    
    private String version;
    
    @NotNull(message = "Survey ID is required")
    private Long surveyId;
} 