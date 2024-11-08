package com.SurveyManager.BACKEND.controller;

import com.SurveyManager.BACKEND.dto.request.SurveyEditionRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SurveyEditionResponseDTO;
import com.SurveyManager.BACKEND.service.SurveyEditionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/survey-editions")
@RequiredArgsConstructor
public class SurveyEditionController {

    private final SurveyEditionService surveyEditionService;

    @PostMapping
    public ResponseEntity<SurveyEditionResponseDTO> create(@Valid @RequestBody SurveyEditionRequestDTO requestDTO) {
        return new ResponseEntity<>(surveyEditionService.create(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyEditionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(surveyEditionService.getById(id));
    }

    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<List<SurveyEditionResponseDTO>> getBySurveyId(@PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyEditionService.getBySurveyId(surveyId));
    }

    @GetMapping("/survey/{surveyId}/year/{year}")
    public ResponseEntity<List<SurveyEditionResponseDTO>> getBySurveyIdAndYear(
            @PathVariable Long surveyId,
            @PathVariable Integer year) {
        return ResponseEntity.ok(surveyEditionService.getBySurveyIdAndYear(surveyId, year));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurveyEditionResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody SurveyEditionRequestDTO requestDTO) {
        return ResponseEntity.ok(surveyEditionService.update(id, requestDTO));
    }

    @PutMapping("/closeSurveyEdition/{id}")
    public ResponseEntity<Map<String, String>> closeSurveyEdition(@PathVariable Long id) {
        surveyEditionService.closeSurveyEdition(id);
        return ResponseEntity.ok(Map.of("message", "Survey edition closed successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        surveyEditionService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 