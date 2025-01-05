package com.SurveyManager.BACKEND.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.SurveyManager.BACKEND.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySubjectId(Long subjectId);
    
    boolean existsBySubjectIdAndOrderIndex(Long subjectId, Integer orderIndex);
    
    Integer countBySubjectId(Long subjectId);

    @Query("SELECT DISTINCT q FROM Question q LEFT JOIN FETCH q.answers " +
            "WHERE q.id IN :questionIds")
    List<Question> findByIdsWithAnswers(@Param("questionIds") List<Long> questionIds);

    
    @Query("SELECT COUNT(q)>0 FROM Question q WHERE q.text = :questionText AND q.subject.id = :subjectId")
    boolean isQuestionAssignedToSubject(@Param("questionText") String questionText, @Param("subjectId") Long subjectId);

    @Query("SELECT COUNT(q)>0 FROM Question q WHERE q.text = :questionText AND q.id != :questionId")
    boolean isQuestionAssignedToSubject2(@Param("questionText") String questionText, @Param("questionId") Long questionId);
} 