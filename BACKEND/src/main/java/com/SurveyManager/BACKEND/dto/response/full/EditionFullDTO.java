package com.SurveyManager.BACKEND.dto.response.full;

import com.SurveyManager.BACKEND.util.constants.EditionStatus;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class EditionFullDTO {
    private Long id;
    private LocalDateTime creationDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer year;
    private EditionStatus status;
    private String version;
    private List<SubjectFullDTO> subjects;
} 