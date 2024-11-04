package com.SurveyManager.BACKEND.service.impl;

import com.SurveyManager.BACKEND.dto.request.SubjectRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SubjectResponseDTO;
import com.SurveyManager.BACKEND.entity.Subject;
import com.SurveyManager.BACKEND.entity.SurveyEdition;
import com.SurveyManager.BACKEND.exception.DuplicateResourceException;
import com.SurveyManager.BACKEND.exception.ResourceNotFoundException;
import com.SurveyManager.BACKEND.mapper.SubjectMapper;
import com.SurveyManager.BACKEND.repository.SubjectRepository;
import com.SurveyManager.BACKEND.repository.SurveyEditionRepository;
import com.SurveyManager.BACKEND.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SurveyEditionRepository surveyEditionRepository;
    private final SubjectMapper subjectMapper;

    @Override
    @Transactional
    public SubjectResponseDTO create(Long surveyEditionId, SubjectRequestDTO requestDTO) {
        // Validate survey edition exists
        SurveyEdition surveyEdition = surveyEditionRepository.findById(surveyEditionId)
            .orElseThrow(() -> new ResourceNotFoundException("SurveyEdition not found with id: " + surveyEditionId));

        // Check for duplicate title within the same survey edition
        if (subjectRepository.existsByTitleAndSurveyEditionId(requestDTO.getTitle(), surveyEditionId)) {
            throw new DuplicateResourceException("Subject with title already exists in this survey edition");
        }

        Subject subject = subjectMapper.toEntity(requestDTO);
        subject.setSurveyEdition(surveyEdition);

        // Set parent subject if parentSubjectId is provided
        if (requestDTO.getParentSubjectId() != null) {
            Subject parentSubject = subjectRepository.findById(requestDTO.getParentSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Parent subject not found"));
            subject.setParentSubject(parentSubject);
            subject.setPath(parentSubject.getPath() + "/" + subject.getTitle());
        } else {
            subject.setPath(subject.getTitle());
        }

        subject = subjectRepository.save(subject);
        return subjectMapper.toResponseDTO(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectResponseDTO getById(Long id) {
        Subject subject = subjectRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));
        return subjectMapper.toResponseDTO(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponseDTO> getBySurveyEditionId(Long surveyEditionId) {
        return subjectRepository.findBySurveyEditionId(surveyEditionId).stream()
            .map(subjectMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponseDTO> getByParentId(Long parentId) {
        return subjectRepository.findByParentSubjectId(parentId).stream()
            .map(subjectMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SubjectResponseDTO update(Long id, SubjectRequestDTO requestDTO) {
        Subject subject = subjectRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + id));

        // Check for duplicate title, excluding current subject
        if (!subject.getTitle().equals(requestDTO.getTitle()) &&
            subjectRepository.existsByTitleAndSurveyEditionId(requestDTO.getTitle(), subject.getSurveyEdition().getId())) {
            throw new DuplicateResourceException("Subject with title already exists in this survey edition");
        }

        subjectMapper.updateEntity(subject, requestDTO);
        subject = subjectRepository.save(subject);
        return subjectMapper.toResponseDTO(subject);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subject not found with id: " + id);
        }
        subjectRepository.deleteById(id);
    }
} 