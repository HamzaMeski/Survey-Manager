package com.SurveyManager.BACKEND.repository;

import com.SurveyManager.BACKEND.entity.SurveyEdition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SurveyEditionRepository extends JpaRepository<SurveyEdition, Long> {
    List<SurveyEdition> findBySurveyId(Long surveyId);
    List<SurveyEdition> findBySurveyIdAndYear(Long surveyId, Integer year);
    @Query("SELECT COUNT(se) > 0 FROM SurveyEdition se WHERE " +
            "se.survey.id = :surveyId AND " +
            "((se.startDate <= :endDate AND se.endDate >= :startDate) OR " +
            "(se.endDate IS NULL AND se.startDate <= :endDate))")
    boolean existsOverlappingEdition(
            @Param("surveyId") Long surveyId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}