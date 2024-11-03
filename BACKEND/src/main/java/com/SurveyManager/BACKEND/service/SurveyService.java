package com.SurveyManager.BACKEND.service;

import com.SurveyManager.BACKEND.dto.request.SurveyRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SurveyResponseDTO;
import java.util.List;

public interface SurveyService {
    SurveyResponseDTO create(SurveyRequestDTO requestDTO);
    SurveyResponseDTO getById(Long id);
    List<SurveyResponseDTO> getAll();
    List<SurveyResponseDTO> getByOwnerId(Long ownerId);
    SurveyResponseDTO update(Long id, SurveyRequestDTO requestDTO);
    void delete(Long id);
} 