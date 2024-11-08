package com.SurveyManager.BACKEND.dto.request;

import com.SurveyManager.BACKEND.util.constants.EditionStatus;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class SurveyEditionRequestDTO {
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    @NotNull(message = "Year is required")
    private Integer year;
    
    private String version;
    
    @NotNull(message = "Survey ID is required")
    private Long surveyId;
} 