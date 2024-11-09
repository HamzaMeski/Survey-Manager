package com.SurveyManager.BACKEND.dto.response.full;

import lombok.Data;
import java.util.List;

@Data
public class SubjectFullDTO {
    private Long id;
    private String title;
    private String description;
    private Integer level;
    private String path;
    private List<SubjectFullDTO> subSubjects;
    private List<QuestionFullDTO> questions;
} 