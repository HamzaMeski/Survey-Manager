package com.SurveyManager.BACKEND.repository;

import com.SurveyManager.BACKEND.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findBySurveyEditionIdAndParentSubjectIsNull(Long surveyEditionId);
    List<Subject> findByParentSubjectId(Long parentId);
    boolean existsByTitleAndSurveyEditionId(String title, Long surveyEditionId);
} 