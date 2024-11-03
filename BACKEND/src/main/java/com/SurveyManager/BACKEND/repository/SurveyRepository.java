package com.SurveyManager.BACKEND.repository;

import com.SurveyManager.BACKEND.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByOwnerId(Long ownerId);
} 