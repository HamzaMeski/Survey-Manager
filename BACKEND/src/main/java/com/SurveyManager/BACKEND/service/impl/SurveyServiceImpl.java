package com.SurveyManager.BACKEND.service.impl;

import com.SurveyManager.BACKEND.dto.request.SurveyRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SurveyResponseDTO;
import com.SurveyManager.BACKEND.entity.Owner;
import com.SurveyManager.BACKEND.entity.Survey;
import com.SurveyManager.BACKEND.exception.ResourceNotFoundException;
import com.SurveyManager.BACKEND.mapper.SurveyMapper;
import com.SurveyManager.BACKEND.repository.OwnerRepository;
import com.SurveyManager.BACKEND.repository.SurveyRepository;
import com.SurveyManager.BACKEND.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {
    
    private final SurveyRepository surveyRepository;
    private final OwnerRepository ownerRepository;
    private final SurveyMapper surveyMapper;
    
    @Override
    @Transactional
    public SurveyResponseDTO create(SurveyRequestDTO requestDTO) {
        Owner owner = ownerRepository.findById(requestDTO.getOwnerId())
            .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + requestDTO.getOwnerId()));

        Survey survey = surveyMapper.toEntity(requestDTO);
        survey.setOwner(owner);
        survey = surveyRepository.save(survey);
        
        return surveyMapper.toResponseDTO(survey);
    }
    
    @Override
    @Transactional(readOnly = true)
    public SurveyResponseDTO getById(Long id) {
        Survey survey = surveyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + id));
        return surveyMapper.toResponseDTO(survey);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SurveyResponseDTO> getAll() {
        return surveyRepository.findAll().stream()
            .map(surveyMapper::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<SurveyResponseDTO> getByOwnerId(Long ownerId) {
        if (!ownerRepository.existsById(ownerId)) {
            throw new ResourceNotFoundException("Owner not found with id: " + ownerId);
        }
        return surveyRepository.findByOwnerId(ownerId).stream()
            .map(surveyMapper::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public SurveyResponseDTO update(Long id, SurveyRequestDTO requestDTO) {
        Survey survey = surveyRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + id));
            
        if (!survey.getOwner().getId().equals(requestDTO.getOwnerId())) {
            Owner newOwner = ownerRepository.findById(requestDTO.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + requestDTO.getOwnerId()));
            survey.setOwner(newOwner);
        }
        
        surveyMapper.updateEntity(survey, requestDTO);
        survey = surveyRepository.save(survey);
        return surveyMapper.toResponseDTO(survey);
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        if (!surveyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Survey not found with id: " + id);
        }
        surveyRepository.deleteById(id);
    }
} 