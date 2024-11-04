package com.SurveyManager.BACKEND.repository;

import com.SurveyManager.BACKEND.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);
    
    boolean existsByQuestionIdAndOrderIndex(Long questionId, Integer orderIndex);
    
    Integer countByQuestionId(Long questionId);
} 