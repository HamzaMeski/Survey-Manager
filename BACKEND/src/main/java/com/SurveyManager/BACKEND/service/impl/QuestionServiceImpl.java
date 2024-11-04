package com.SurveyManager.BACKEND.service.impl;

import com.SurveyManager.BACKEND.dto.request.QuestionRequestDTO;
import com.SurveyManager.BACKEND.dto.response.QuestionResponseDTO;
import com.SurveyManager.BACKEND.entity.Question;
import com.SurveyManager.BACKEND.entity.Subject;
import com.SurveyManager.BACKEND.exception.ResourceNotFoundException;
import com.SurveyManager.BACKEND.exception.ValidationException;
import com.SurveyManager.BACKEND.mapper.QuestionMapper;
import com.SurveyManager.BACKEND.repository.QuestionRepository;
import com.SurveyManager.BACKEND.repository.SubjectRepository;
import com.SurveyManager.BACKEND.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final SubjectRepository subjectRepository;
    private final QuestionMapper questionMapper;

    @Override
    @Transactional
    public QuestionResponseDTO create(Long subjectId, QuestionRequestDTO requestDTO) {
        Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + subjectId));

        validateSubjectForQuestions(subject);
        
        Question question = questionMapper.toEntity(requestDTO);
        question.setSubject(subject);
        
        // Set order index if not provided
        if (question.getOrderIndex() == null) {
            Integer lastOrderIndex = questionRepository.countBySubjectId(subjectId);
            question.setOrderIndex(lastOrderIndex + 1);
        } else {
            // Validate order index doesn't exist
            if (questionRepository.existsBySubjectIdAndOrderIndex(subjectId, question.getOrderIndex())) {
                throw new ValidationException("Question with order index " + question.getOrderIndex() 
                    + " already exists in this subject");
            }
        }

        question.setAnswerCount(0); // Initialize answer count
        question = questionRepository.save(question);
        
        return questionMapper.toResponseDTO(question);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionResponseDTO getById(Long id) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        return questionMapper.toResponseDTO(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionResponseDTO> getBySubjectId(Long subjectId) {
        return questionRepository.findBySubjectId(subjectId).stream()
            .map(questionMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuestionResponseDTO update(Long id, QuestionRequestDTO requestDTO) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
        
        // If order index is changing, validate new position
        if (requestDTO.getOrderIndex() != null && 
            !requestDTO.getOrderIndex().equals(question.getOrderIndex()) &&
            questionRepository.existsBySubjectIdAndOrderIndex(
                question.getSubject().getId(), requestDTO.getOrderIndex())) {
            throw new ValidationException("Question with order index " + requestDTO.getOrderIndex() 
                + " already exists in this subject");
        }

        questionMapper.updateEntity(question, requestDTO);
        question = questionRepository.save(question);
        
        return questionMapper.toResponseDTO(question);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Question not found with id: " + id);
        }
        questionRepository.deleteById(id);
    }

    private void validateSubjectForQuestions(Subject subject) {
        // Check if subject has children
        if (!subject.getSubSubjects().isEmpty()) {
            throw new ValidationException(
                "Cannot add questions to subject with children: " + subject.getTitle()
            );
        }

        // Get parent path
        String parentPath = getParentPath(subject.getPath());
        
        // Check for siblings
        if (hasSiblings(subject, parentPath)) {
            throw new ValidationException(
                "Cannot add questions to subject with siblings: " + subject.getTitle()
            );
        }
    }

    private String getParentPath(String path) {
        int lastIndex = path.lastIndexOf("/");
        return lastIndex > 0 ? path.substring(0, lastIndex) : "";
    }

    private boolean hasSiblings(Subject subject, String parentPath) {
        return subjectRepository.countByParentPathAndLevel(parentPath, subject.getLevel()) > 1;
    }
} 