package com.SurveyManager.BACKEND.dto.response;

import com.SurveyManager.BACKEND.util.constants.EditionStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SurveyEditionResponseDTO {
    private Long id;
    private LocalDateTime creationDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer year;
    private EditionStatus status;
    private String version;
    private Long surveyId;
} 