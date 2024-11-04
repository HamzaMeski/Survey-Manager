package com.SurveyManager.BACKEND.service;

import com.SurveyManager.BACKEND.dto.request.SubjectRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SubjectResponseDTO;
import java.util.List;

public interface SubjectService {
    SubjectResponseDTO create(Long surveyEditionId, SubjectRequestDTO requestDTO);
    SubjectResponseDTO getById(Long id);
    List<SubjectResponseDTO> getBySurveyEditionId(Long surveyEditionId);
    List<SubjectResponseDTO> getByParentId(Long parentId);
    SubjectResponseDTO update(Long id, SubjectRequestDTO requestDTO);
    void delete(Long id);
} 