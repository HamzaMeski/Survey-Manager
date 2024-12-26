package com.SurveyManager.BACKEND.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SurveyManager.BACKEND.dto.request.SurveyRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SurveyResponseDTO;
import com.SurveyManager.BACKEND.dto.response.full.SurveyFullResponseDTO;
import com.SurveyManager.BACKEND.entity.Owner;
import com.SurveyManager.BACKEND.entity.Question;
import com.SurveyManager.BACKEND.entity.Subject;
import com.SurveyManager.BACKEND.entity.Survey;
import com.SurveyManager.BACKEND.entity.SurveyEdition;
import com.SurveyManager.BACKEND.exception.ResourceNotFoundException;
import com.SurveyManager.BACKEND.exception.ValidationException;
import com.SurveyManager.BACKEND.mapper.SurveyFullMapper;
import com.SurveyManager.BACKEND.mapper.SurveyMapper;
import com.SurveyManager.BACKEND.repository.OwnerRepository;
import com.SurveyManager.BACKEND.repository.QuestionRepository;
import com.SurveyManager.BACKEND.repository.SubjectRepository;
import com.SurveyManager.BACKEND.repository.SurveyRepository;
import com.SurveyManager.BACKEND.service.SurveyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;
    private final OwnerRepository ownerRepository;
    private final SurveyMapper surveyMapper;
    private final SurveyFullMapper surveyFullMapper;
    private final SubjectRepository subjectRepository;
    private final QuestionRepository questionRepository;
    
    @Override
    @Transactional
    public SurveyResponseDTO create(SurveyRequestDTO requestDTO) {
        Owner owner = ownerRepository.findById(requestDTO.getOwnerId())
            .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id: " + requestDTO.getOwnerId()));
        

        if(surveyRepository.existsByTitle(requestDTO.getTitle())) {
            throw new ValidationException("Survey already exists");
        }
        
        Survey survey = surveyMapper.toEntity(requestDTO);
        survey.setOwner(owner);
        survey = surveyRepository.save(survey);
        
        return surveyMapper.toResponseDTO(survey);
    }

    @Override
    @Transactional(readOnly = true)
    public SurveyFullResponseDTO getFullSurvey(Long id) {
        // 1. Get survey with editions
        Survey survey = surveyRepository.findByIdWithEditions(id)
                .orElseThrow(() -> new ResourceNotFoundException("Survey not found with id: " + id));

        // 2. Get all edition IDs
        List<Long> editionIds = survey.getEditions().stream()
                .map(SurveyEdition::getId)
                .collect(Collectors.toList());

        // 3. Get subjects for these editions
        List<Subject> subjects = subjectRepository.findByEditionIdsWithQuestions(editionIds);

        // 4. Get questions with answers
        List<Long> questionIds = subjects.stream()
                .flatMap(s -> s.getQuestions().stream())
                .map(Question::getId)
                .collect(Collectors.toList());

        List<Question> questions = questionRepository.findByIdsWithAnswers(questionIds);

        // 5. Create a map for quick lookup
        Map<Long, Question> questionMap = questions.stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        // 6. Update questions with their answers
        subjects.forEach(subject ->
                subject.getQuestions().forEach(q ->
                        q.setAnswers(questionMap.get(q.getId()).getAnswers())
                )
        );

        // 7. Group subjects by edition
        Map<Long, List<Subject>> editionSubjectsMap = subjects.stream()
                .collect(Collectors.groupingBy(s -> s.getSurveyEdition().getId()));

        // 8. Set subjects to editions
        survey.getEditions().forEach(edition ->
                edition.setSubjects(editionSubjectsMap.getOrDefault(edition.getId(), new ArrayList<>()))
        );

        return surveyFullMapper.toFullResponseDTO(survey);
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