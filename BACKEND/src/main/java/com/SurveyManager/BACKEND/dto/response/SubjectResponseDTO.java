package com.SurveyManager.BACKEND.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SubjectResponseDTO extends BaseResponseDTO {
    private String title;
    private String description;
    private Long parentSubjectId;
    private List<SubjectResponseDTO> subSubjects;
    private List<QuestionResponseDTO> questions;
    private Integer level;
    private String path;
} 
