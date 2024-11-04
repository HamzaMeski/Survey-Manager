package com.SurveyManager.BACKEND.service;

import com.SurveyManager.BACKEND.dto.request.AnswerRequestDTO;
import com.SurveyManager.BACKEND.dto.response.AnswerResponseDTO;
import java.util.List;

public interface AnswerService {
    AnswerResponseDTO create(Long questionId, AnswerRequestDTO requestDTO);
    AnswerResponseDTO getById(Long id);
    List<AnswerResponseDTO> getByQuestionId(Long questionId);
    AnswerResponseDTO update(Long id, AnswerRequestDTO requestDTO);
    void delete(Long id);
} 