package com.SurveyManager.BACKEND.service.impl;

import com.SurveyManager.BACKEND.dto.request.AnswerRequestDTO;
import com.SurveyManager.BACKEND.dto.response.AnswerResponseDTO;
import com.SurveyManager.BACKEND.entity.Answer;
import com.SurveyManager.BACKEND.entity.Question;
import com.SurveyManager.BACKEND.exception.DuplicateResourceException;
import com.SurveyManager.BACKEND.exception.ResourceNotFoundException;
import com.SurveyManager.BACKEND.exception.ValidationException;
import com.SurveyManager.BACKEND.mapper.AnswerMapper;
import com.SurveyManager.BACKEND.repository.AnswerRepository;
import com.SurveyManager.BACKEND.repository.QuestionRepository;
import com.SurveyManager.BACKEND.service.AnswerService;
import com.SurveyManager.BACKEND.util.constants.QuestionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final AnswerMapper answerMapper;

    @Override
    @Transactional
    public AnswerResponseDTO create(Long questionId, AnswerRequestDTO requestDTO) {
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + questionId));

        Answer answer = answerMapper.toEntity(requestDTO);
        answer.setQuestion(question);
        answer.setSelectionCount(0);

        // Set order index if not provided
        if (answer.getOrderIndex() == null) {
            Integer lastOrderIndex = answerRepository.countByQuestionId(questionId);
            answer.setOrderIndex(lastOrderIndex + 1);
        } else {
            // Validate order index doesn't exist
            if (answerRepository.existsByQuestionIdAndOrderIndex(questionId, answer.getOrderIndex())) {
                throw new ValidationException("Answer with order index " + answer.getOrderIndex() 
                    + " already exists in this question");
            }
        }

        answer = answerRepository.save(answer);

        // Update question's answer count
        question.setAnswerCount(question.getAnswerCount() + 1);
        questionRepository.save(question);

        return answerMapper.toResponseDTO(answer);
    }

    @Override
    @Transactional(readOnly = true)
    public AnswerResponseDTO getById(Long id) {
        Answer answer = answerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id: " + id));
        return answerMapper.toResponseDTO(answer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnswerResponseDTO> getByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId).stream()
            .map(answerMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AnswerResponseDTO update(Long id, AnswerRequestDTO requestDTO) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id: " + id));

//        if (requestDTO.getOrderIndex() != null &&
//                !requestDTO.getOrderIndex().equals(answer.getOrderIndex()) &&
//                answerRepository.existsByQuestionIdAndOrderIndex(
//                        answer.getQuestion().getId(), requestDTO.getOrderIndex())) {
//            throw new ValidationException("Answer with order index " + requestDTO.getOrderIndex()
//                    + " already exists in this question");
//        }


        answerMapper.updateEntity(answer, requestDTO);
        answer = answerRepository.save(answer);

        return answerMapper.toResponseDTO(answer);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Answer answer = answerRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Answer not found with id: " + id));

        Question question = answer.getQuestion();
        
        answerRepository.deleteById(id);

        // Update question's answer count
        question.setAnswerCount(question.getAnswerCount() - 1);
        questionRepository.save(question);
    }

    @Override
    @Transactional
    public void participateMultiChoiceAnswer(List<Long> answersIDs) {
       for(Long answerId : answersIDs) {
           participateSingleChoiceAnswer(answerId);
       }
    }

    @Override
    @Transactional
    public void participateSingleChoiceAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new ResourceNotFoundException("Answer not found with ID "+ answerId));

        answer.setSelectionCount(answer.getSelectionCount() + 1);
        answerRepository.save(answer);
    }
} 