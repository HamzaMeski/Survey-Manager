package com.SurveyManager.BACKEND.scheduler;

import com.SurveyManager.BACKEND.repository.SurveyEditionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
@Slf4j
public class SurveyEditionStatusScheduler {

    private final SurveyEditionRepository surveyEditionRepository;

    // Run once every day at midnight (00:00)
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void updateEditionStatuses() {
        try {
            // Update DRAFT to PUBLISHED
            surveyEditionRepository.updateDraftToPublished();

            // Update PUBLISHED to CLOSED
            surveyEditionRepository.updatePublishedToClosed();

            log.info("Survey edition statuses updated successfully");
        } catch (Exception e) {
            log.error("Failed to update survey edition statuses", e);
        }
    }
}
