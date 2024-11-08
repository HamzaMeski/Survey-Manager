package com.SurveyManager.BACKEND.service.impl;

import com.SurveyManager.BACKEND.dto.request.SurveyEditionRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SurveyEditionResponseDTO;
import com.SurveyManager.BACKEND.entity.Survey;
import com.SurveyManager.BACKEND.entity.SurveyEdition;
import com.SurveyManager.BACKEND.exception.ResourceNotFoundException;
import com.SurveyManager.BACKEND.exception.ValidationException;
import com.SurveyManager.BACKEND.mapper.SurveyEditionMapper;
import com.SurveyManager.BACKEND.repository.SurveyEditionRepository;
import com.SurveyManager.BACKEND.repository.SurveyRepository;
import com.SurveyManager.BACKEND.service.SurveyEditionService;
import com.SurveyManager.BACKEND.util.constants.EditionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SurveyEditionServiceImpl implements SurveyEditionService {

    private final SurveyEditionRepository surveyEditionRepository;
    private final SurveyRepository surveyRepository;
    private final SurveyEditionMapper surveyEditionMapper;

    @Override
    @Transactional
    public SurveyEditionResponseDTO create(SurveyEditionRequestDTO requestDTO) {
        Survey survey = surveyRepository.findById(requestDTO.getSurveyId())
            .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + requestDTO.getSurveyId()));

        // making sure that the survey edition start_time don't overlap with the past time
        if(requestDTO.getStartDate().isBefore(LocalDateTime.now())) {
            throw new ValidationException("start time of survey edition shouldn't be in the past");
        }

        if(requestDTO.getEndDate() != null) {
            if(requestDTO.getEndDate().isBefore(requestDTO.getStartDate())) {
                throw new ValidationException("end time must be after start time");
            }
        }

        // making sure that the survey edition duration don't overlap with a duration of other survey editions of the same survey
        if(surveyEditionRepository.existsOverlappingEdition(requestDTO.getSurveyId(), requestDTO.getStartDate(), requestDTO.getEndDate())) {
            throw new ValidationException("duration between start time and end time overlap with duration of other survey editions of the same survey");
        }

        // making sure that the year of the edition is the same in start and end dates
        if(!(requestDTO.getStartDate().getYear() == requestDTO.getYear() && requestDTO.getEndDate().getYear() == requestDTO.getYear())) {
            throw new ValidationException("the year of start and end dates should be same as year!");
        }

        SurveyEdition surveyEdition = surveyEditionMapper.toEntity(requestDTO);
        surveyEdition.setSurvey(survey);
        surveyEdition = surveyEditionRepository.save(surveyEdition);
        
        return surveyEditionMapper.toResponseDTO(surveyEdition);
    }

    @Override
    @Transactional(readOnly = true)
    public SurveyEditionResponseDTO getById(Long id) {
        SurveyEdition surveyEdition = surveyEditionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Survey Edition not found with id: " + id));
        return surveyEditionMapper.toResponseDTO(surveyEdition);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SurveyEditionResponseDTO> getBySurveyId(Long surveyId) {
        if (!surveyRepository.existsById(surveyId)) {
            throw new ResourceNotFoundException("Survey not found with id: " + surveyId);
        }
        return surveyEditionRepository.findBySurveyId(surveyId).stream()
            .map(surveyEditionMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SurveyEditionResponseDTO> getBySurveyIdAndYear(Long surveyId, Integer year) {
        if (!surveyRepository.existsById(surveyId)) {
            throw new ResourceNotFoundException("Survey not found with id: " + surveyId);
        }
        return surveyEditionRepository.findBySurveyIdAndYear(surveyId, year).stream()
            .map(surveyEditionMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SurveyEditionResponseDTO update(Long id, SurveyEditionRequestDTO requestDTO) {
        SurveyEdition surveyEdition = surveyEditionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Survey Edition not found with id: " + id));

        if (!surveyEdition.getSurvey().getId().equals(requestDTO.getSurveyId())) {
            Survey newSurvey = surveyRepository.findById(requestDTO.getSurveyId())
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + requestDTO.getSurveyId()));
            surveyEdition.setSurvey(newSurvey);
        }

        surveyEditionMapper.updateEntity(surveyEdition, requestDTO);
        surveyEdition = surveyEditionRepository.save(surveyEdition);
        return surveyEditionMapper.toResponseDTO(surveyEdition);
    }

    @Override
    @Transactional
    public void closeSurveyEdition(Long id) {
        SurveyEdition surveyEdition = surveyEditionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("survey edition not found with id:" + id));

        surveyEdition.setStatus(EditionStatus.CLOSED);
        surveyEditionRepository.save(surveyEdition);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!surveyEditionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Survey Edition not found with id: " + id);
        }
        surveyEditionRepository.deleteById(id);
    }
} 