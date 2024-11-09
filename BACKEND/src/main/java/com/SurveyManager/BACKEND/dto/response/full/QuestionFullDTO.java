package com.SurveyManager.BACKEND.dto.response.full;

import com.SurveyManager.BACKEND.dto.response.AnswerResponseDTO;
import lombok.Data;
import java.util.List;

@Data
public class QuestionFullDTO {
    private Long id;
    private String text;
    private Integer orderIndex;
    private List<AnswerResponseDTO> answers;
} 