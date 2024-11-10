package com.SurveyManager.BACKEND.controller;

import com.SurveyManager.BACKEND.dto.request.SurveyRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SurveyResponseDTO;
import com.SurveyManager.BACKEND.dto.response.full.SurveyFullResponseDTO;
import com.SurveyManager.BACKEND.service.SurveyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
@Tag(name = "Survey", description = "Survey management APIs")
public class SurveyController {

    private final SurveyService surveyService;

    @Operation(
            summary = "Create new survey",
            description = "Creates a new survey with the provided details"
    )
    @PostMapping
    public ResponseEntity<SurveyResponseDTO> create(
            @Parameter(description = "Survey details to create", required = true)
            @Valid @RequestBody SurveyRequestDTO requestDTO) {
        return new ResponseEntity<>(surveyService.create(requestDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get survey by ID",
            description = "Retrieves basic survey information by ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<SurveyResponseDTO> getById(
            @Parameter(description = "ID of the survey to retrieve", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(surveyService.getById(id));
    }

    @Operation(
            summary = "Get complete survey details",
            description = "Retrieves full survey information including all editions, subjects, and questions"
    )
    @GetMapping("/{id}/full")
    public ResponseEntity<SurveyFullResponseDTO> getFullSurvey(
            @Parameter(description = "ID of the survey to retrieve full details", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(surveyService.getFullSurvey(id));
    }

    @Operation(
            summary = "Get all surveys",
            description = "Retrieves a list of all surveys in the system"
    )
    @GetMapping
    public ResponseEntity<List<SurveyResponseDTO>> getAll() {
        return ResponseEntity.ok(surveyService.getAll());
    }

    @Operation(
            summary = "Get surveys by owner",
            description = "Retrieves all surveys owned by a specific owner"
    )
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<SurveyResponseDTO>> getByOwnerId(
            @Parameter(description = "ID of the owner to get surveys for", example = "1")
            @PathVariable Long ownerId) {
        return ResponseEntity.ok(surveyService.getByOwnerId(ownerId));
    }

    @Operation(
            summary = "Update survey",
            description = "Updates an existing survey's information"
    )
    @PutMapping("/{id}")
    public ResponseEntity<SurveyResponseDTO> update(
            @Parameter(description = "ID of the survey to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated survey details", required = true)
            @Valid @RequestBody SurveyRequestDTO requestDTO) {
        return ResponseEntity.ok(surveyService.update(id, requestDTO));
    }

    @Operation(
            summary = "Delete survey",
            description = "Removes a survey and all its associated data from the system"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the survey to delete", example = "1")
            @PathVariable Long id) {
        surveyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}