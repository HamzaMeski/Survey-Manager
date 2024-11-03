package com.SurveyManager.BACKEND.repository;

import com.SurveyManager.BACKEND.entity.SurveyEdition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SurveyEditionRepository extends JpaRepository<SurveyEdition, Long> {
    List<SurveyEdition> findBySurveyId(Long surveyId);
    List<SurveyEdition> findBySurveyIdAndYear(Long surveyId, Integer year);
} 