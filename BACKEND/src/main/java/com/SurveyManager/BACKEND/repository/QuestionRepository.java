package com.SurveyManager.BACKEND.repository;

import com.SurveyManager.BACKEND.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySubjectId(Long subjectId);
    
    boolean existsBySubjectIdAndOrderIndex(Long subjectId, Integer orderIndex);
    
    Integer countBySubjectId(Long subjectId);

    @Query("SELECT DISTINCT q FROM Question q LEFT JOIN FETCH q.answers " +
            "WHERE q.id IN :questionIds")
    List<Question> findByIdsWithAnswers(@Param("questionIds") List<Long> questionIds);
} 