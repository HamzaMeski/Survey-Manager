package com.SurveyManager.BACKEND.dto.response;

import com.SurveyManager.BACKEND.util.constants.SurveyStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SurveyResponseDTO extends BaseResponseDTO {
    private String title;
    private String description;
    private SurveyStatus status;
    private String category;
    private String targetAudience;
    private OwnerResponseDTO owner;
    private List<SurveyEditionResponseDTO> editions;
} 