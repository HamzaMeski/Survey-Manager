package com.SurveyManager.BACKEND.repository;

import com.SurveyManager.BACKEND.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySubjectId(Long subjectId);
    
    boolean existsBySubjectIdAndOrderIndex(Long subjectId, Integer orderIndex);
    
    Integer countBySubjectId(Long subjectId);
} 