package com.SurveyManager.BACKEND.controller;

import com.SurveyManager.BACKEND.dto.request.SurveyEditionRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SurveyEditionResponseDTO;
import com.SurveyManager.BACKEND.service.SurveyEditionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Survey Edition", description = "Survey Edition management APIs")
public class SurveyEditionController {

    private final SurveyEditionService surveyEditionService;

    @Operation(
            summary = "Create new survey edition",
            description = "Creates a new edition for a survey with specified details"
    )
    @PostMapping
    public ResponseEntity<SurveyEditionResponseDTO> create(
            @Parameter(description = "Survey edition details to create", required = true)
            @Valid @RequestBody SurveyEditionRequestDTO requestDTO) {
        return new ResponseEntity<>(surveyEditionService.create(requestDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get survey edition by ID",
            description = "Retrieves a specific survey edition's information"
    )
    @GetMapping("/{id}")
    public ResponseEntity<SurveyEditionResponseDTO> getById(
            @Parameter(description = "ID of the survey edition to retrieve", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(surveyEditionService.getById(id));
    }

    @Operation(
            summary = "Get editions by survey",
            description = "Retrieves all editions for a specific survey"
    )
    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<List<SurveyEditionResponseDTO>> getBySurveyId(
            @Parameter(description = "ID of the survey to get editions for", example = "1")
            @PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyEditionService.getBySurveyId(surveyId));
    }

    @Operation(
            summary = "Get editions by survey and year",
            description = "Retrieves all editions for a specific survey in a given year"
    )
    @GetMapping("/survey/{surveyId}/year/{year}")
    public ResponseEntity<List<SurveyEditionResponseDTO>> getBySurveyIdAndYear(
            @Parameter(description = "ID of the survey", example = "1")
            @PathVariable Long surveyId,
            @Parameter(description = "Year to filter editions", example = "2024")
            @PathVariable Integer year) {
        return ResponseEntity.ok(surveyEditionService.getBySurveyIdAndYear(surveyId, year));
    }

    @Operation(
            summary = "Update survey edition",
            description = "Updates an existing survey edition's information"
    )
    @PutMapping("/{id}")
    public ResponseEntity<SurveyEditionResponseDTO> update(
            @Parameter(description = "ID of the survey edition to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated survey edition details", required = true)
            @Valid @RequestBody SurveyEditionRequestDTO requestDTO) {
        return ResponseEntity.ok(surveyEditionService.update(id, requestDTO));
    }

    @Operation(
            summary = "Close survey edition",
            description = "Marks a survey edition as closed, preventing further responses"
    )
    @PutMapping("/closeSurveyEdition/{id}")
    public ResponseEntity<Map<String, String>> closeSurveyEdition(
            @Parameter(description = "ID of the survey edition to close", example = "1")
            @PathVariable Long id) {
        surveyEditionService.closeSurveyEdition(id);
        return ResponseEntity.ok(Map.of("message", "Survey edition closed successfully"));
    }

    @Operation(
            summary = "Delete survey edition",
            description = "Removes a survey edition and its associated data from the system"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the survey edition to delete", example = "1")
            @PathVariable Long id) {
        surveyEditionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}