package com.SurveyManager.BACKEND.service;

import com.SurveyManager.BACKEND.dto.request.SurveyEditionRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SurveyEditionResponseDTO;
import java.util.List;

public interface SurveyEditionService {
    SurveyEditionResponseDTO create(SurveyEditionRequestDTO requestDTO);
    SurveyEditionResponseDTO getById(Long id);
    List<SurveyEditionResponseDTO> getBySurveyId(Long surveyId);
    List<SurveyEditionResponseDTO> getBySurveyIdAndYear(Long surveyId, Integer year);
    SurveyEditionResponseDTO update(Long id, SurveyEditionRequestDTO requestDTO);
    void delete(Long id);
} 