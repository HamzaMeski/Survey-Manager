package com.SurveyManager.BACKEND.repository;

import com.SurveyManager.BACKEND.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByOwnerId(Long ownerId);

    @Query("SELECT DISTINCT s FROM Survey s LEFT JOIN FETCH s.editions WHERE s.id = :id")
    Optional<Survey> findByIdWithEditions(@Param("id") Long id);
} 