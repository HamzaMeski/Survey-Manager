package com.SurveyManager.BACKEND.service.impl;

import com.SurveyManager.BACKEND.dto.request.AnswerRequestDTO;
import com.SurveyManager.BACKEND.dto.response.AnswerResponseDTO;
import com.SurveyManager.BACKEND.entity.Answer;
import com.SurveyManager.BACKEND.entity.Question;
import com.SurveyManager.BACKEND.exception.ResourceNotFoundException;
import com.SurveyManager.BACKEND.exception.ValidationException;
import com.SurveyManager.BACKEND.mapper.AnswerMapper;
import com.SurveyManager.BACKEND.repository.AnswerRepository;
import com.SurveyManager.BACKEND.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnswerServiceImplTest {

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AnswerMapper answerMapper;

    @InjectMocks
    private AnswerServiceImpl answerService;

    private Answer testAnswer;
    private Question testQuestion;
    private AnswerRequestDTO testRequestDTO;
    private AnswerResponseDTO testResponseDTO;

    @BeforeEach
    void setUp() {
        // Initialize test question
        testQuestion = new Question();
        testQuestion.setId(1L);
        testQuestion.setAnswerCount(2);

        // Initialize test answer
        testAnswer = new Answer();
        testAnswer.setId(1L);
        testAnswer.setText("Test Answer");
        testAnswer.setOrderIndex(1);
        testAnswer.setSelectionCount(0);
        testAnswer.setQuestion(testQuestion);

        // Initialize test request DTO
        testRequestDTO = new AnswerRequestDTO();
        testRequestDTO.setText("Test Answer");
        testRequestDTO.setOrderIndex(1);

        // Initialize test response DTO
        testResponseDTO = new AnswerResponseDTO();
        testResponseDTO.setId(1L);
        testResponseDTO.setText("Test Answer");
        testResponseDTO.setOrderIndex(1);
    }

    @Test
    void create_ShouldCreateAnswer_WhenValidDataProvided() {
        // Arrange
        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(testQuestion));
        when(answerMapper.toEntity(testRequestDTO))
                .thenReturn(testAnswer);
        when(answerRepository.save(any(Answer.class)))
                .thenReturn(testAnswer);
        when(answerMapper.toResponseDTO(testAnswer))
                .thenReturn(testResponseDTO);

        // Act
        AnswerResponseDTO result = answerService.create(1L, testRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testResponseDTO.getText(), result.getText());
        assertEquals(3, testQuestion.getAnswerCount());

        // Verify interactions
        verify(questionRepository).findById(1L);
        verify(answerMapper).toEntity(testRequestDTO);
        verify(answerRepository).save(any(Answer.class));
        verify(questionRepository).save(testQuestion);
        verify(answerMapper).toResponseDTO(testAnswer);
    }

    @Test
    void create_ShouldSetOrderIndex_WhenNotProvided() {
        // Arrange
        testRequestDTO.setOrderIndex(null);
        testAnswer.setOrderIndex(null);

        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(testQuestion));
        when(answerMapper.toEntity(testRequestDTO))
                .thenReturn(testAnswer);
        when(answerRepository.countByQuestionId(1L))
                .thenReturn(2);
        when(answerRepository.save(any(Answer.class)))
                .thenReturn(testAnswer);
        when(answerMapper.toResponseDTO(testAnswer))
                .thenReturn(testResponseDTO);

        // Act
        answerService.create(1L, testRequestDTO);

        // Assert
        verify(answerRepository).save(argThat(answer ->
                answer.getOrderIndex() == 3  // Should be count + 1
        ));
    }

    @Test
    void create_ShouldThrowException_WhenDuplicateOrderIndex() {
        // Arrange
        when(questionRepository.findById(1L))
                .thenReturn(Optional.of(testQuestion));
        when(answerMapper.toEntity(testRequestDTO))
                .thenReturn(testAnswer);
        when(answerRepository.existsByQuestionIdAndOrderIndex(1L, 1))
                .thenReturn(true);

        // Act & Assert
        assertThrows(ValidationException.class, () ->
                answerService.create(1L, testRequestDTO));
        verify(answerRepository, never()).save(any());
    }

    @Test
    void create_ShouldThrowException_WhenQuestionNotFound() {
        // Arrange
        when(questionRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                answerService.create(999L, testRequestDTO));
        verify(answerMapper, never()).toEntity(any());
        verify(answerRepository, never()).save(any());
    }

    @Test
    void getById_ShouldReturnAnswer_WhenAnswerExists() {
        // Arrange
        when(answerRepository.findById(1L))
                .thenReturn(Optional.of(testAnswer));
        when(answerMapper.toResponseDTO(testAnswer))
                .thenReturn(testResponseDTO);

        // Act
        AnswerResponseDTO result = answerService.getById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testResponseDTO.getText(), result.getText());
        verify(answerRepository).findById(1L);
        verify(answerMapper).toResponseDTO(testAnswer);
    }

    @Test
    void getById_ShouldThrowException_WhenAnswerNotFound() {
        // Arrange
        when(answerRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                answerService.getById(999L));
        verify(answerMapper, never()).toResponseDTO(any());
    }

    @Test
    void getByQuestionId_ShouldReturnAnswers() {
        // Arrange
        List<Answer> answers = List.of(testAnswer);
        when(answerRepository.findByQuestionId(1L))
                .thenReturn(answers);
        when(answerMapper.toResponseDTO(testAnswer))
                .thenReturn(testResponseDTO);

        // Act
        List<AnswerResponseDTO> results = answerService.getByQuestionId(1L);

        // Assert
        assertFalse(results.isEmpty());
        assertEquals(1, results.size());
        assertEquals(testResponseDTO.getText(), results.get(0).getText());
        verify(answerRepository).findByQuestionId(1L);
        verify(answerMapper).toResponseDTO(testAnswer);
    }

    @Test
    void update_ShouldUpdateAnswer_WhenValidDataProvided() {
        // Arrange
        when(answerRepository.findById(1L))
                .thenReturn(Optional.of(testAnswer));
        when(answerRepository.save(any(Answer.class)))
                .thenReturn(testAnswer);
        when(answerMapper.toResponseDTO(testAnswer))
                .thenReturn(testResponseDTO);

        // Act
        AnswerResponseDTO result = answerService.update(1L, testRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(testResponseDTO.getText(), result.getText());
        verify(answerMapper).updateEntity(testAnswer, testRequestDTO);
        verify(answerRepository).save(testAnswer);
    }

    @Test
    void update_ShouldThrowException_WhenAnswerNotFound() {
        // Arrange
        when(answerRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                answerService.update(999L, testRequestDTO));
        verify(answerMapper, never()).updateEntity(any(), any());
        verify(answerRepository, never()).save(any());
    }

    @Test
    void delete_ShouldDeleteAndUpdateQuestion_WhenAnswerExists() {
        // Arrange
        when(answerRepository.findById(1L))
                .thenReturn(Optional.of(testAnswer));

        // Act
        answerService.delete(1L);

        // Assert
        assertEquals(1, testQuestion.getAnswerCount());
        verify(answerRepository).deleteById(1L);
        verify(questionRepository).save(testQuestion);
    }

    @Test
    void delete_ShouldThrowException_WhenAnswerNotFound() {
        // Arrange
        when(answerRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () ->
                answerService.delete(999L));
        verify(answerRepository, never()).deleteById(any());
        verify(questionRepository, never()).save(any());
    }

    @Test
    void participateSingleChoiceAnswer_ShouldIncrementCount() {
        // Arrange
        when(answerRepository.findById(1L))
                .thenReturn(Optional.of(testAnswer));

        // Act
        answerService.participateSingleChoiceAnswer(1L);

        // Assert
        assertEquals(1, testAnswer.getSelectionCount());
        verify(answerRepository).save(testAnswer);
    }

    @Test
    void participateMultiChoiceAnswer_ShouldIncrementAllCounts() {
        // Arrange
        List<Long> answerIds = List.of(1L, 2L);
        Answer answer1 = new Answer();
        Answer answer2 = new Answer();
        answer1.setSelectionCount(0);
        answer2.setSelectionCount(0);

        when(answerRepository.findById(1L))
                .thenReturn(Optional.of(answer1));
        when(answerRepository.findById(2L))
                .thenReturn(Optional.of(answer2));

        // Act
        answerService.participateMultiChoiceAnswer(answerIds);

        // Assert
        assertEquals(1, answer1.getSelectionCount());
        assertEquals(1, answer2.getSelectionCount());
        verify(answerRepository, times(2)).save(any(Answer.class));
    }
}