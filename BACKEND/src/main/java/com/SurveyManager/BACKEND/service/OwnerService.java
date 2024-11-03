package com.SurveyManager.BACKEND.service;

import com.SurveyManager.BACKEND.dto.request.OwnerRequestDTO;
import com.SurveyManager.BACKEND.dto.response.OwnerResponseDTO;
import java.util.List;

public interface OwnerService {
    OwnerResponseDTO create(OwnerRequestDTO requestDTO);
    OwnerResponseDTO getById(Long id);
    List<OwnerResponseDTO> getAll();
    OwnerResponseDTO update(Long id, OwnerRequestDTO requestDTO);
    void delete(Long id);
    boolean existsByEmail(String email);
} 