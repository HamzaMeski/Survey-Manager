package com.SurveyManager.BACKEND.service;

import com.SurveyManager.BACKEND.dto.request.QuestionRequestDTO;
import com.SurveyManager.BACKEND.dto.response.QuestionResponseDTO;
import java.util.List;

public interface QuestionService {
    QuestionResponseDTO create(Long subjectId, QuestionRequestDTO requestDTO);
    QuestionResponseDTO getById(Long id);
    List<QuestionResponseDTO> getBySubjectId(Long subjectId);
    QuestionResponseDTO update(Long id, QuestionRequestDTO requestDTO);
    void delete(Long id);
} 