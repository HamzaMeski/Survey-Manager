package com.SurveyManager.BACKEND.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SurveyManager.BACKEND.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);
    
    boolean existsByQuestionIdAndOrderIndex(Long questionId, Integer orderIndex);
    
    Integer countByQuestionId(Long questionId);

    @Query("SELECT COUNT(a) > 0 FROM Answer a WHERE a.text = :answerText AND a.question.id = :questionId")
    boolean isAnswerAssignedToQuestion(@Param("answerText") String answerText, @Param("questionId") Long questionId);
}