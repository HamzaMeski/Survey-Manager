package com.SurveyManager.BACKEND.controller;

import com.SurveyManager.BACKEND.dto.request.SubjectRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SubjectResponseDTO;
import com.SurveyManager.BACKEND.service.SubjectService;
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
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
@Tag(name = "Subject", description = "Subject management APIs")
public class SubjectController {

    private final SubjectService subjectService;

    @Operation(
            summary = "Create new subject",
            description = "Creates a new subject for a specific survey edition"
    )
    @PostMapping("/survey-edition/{surveyEditionId}")
    public ResponseEntity<SubjectResponseDTO> create(
            @Parameter(description = "ID of the survey edition to add subject to", example = "1")
            @PathVariable Long surveyEditionId,
            @Parameter(description = "Subject details to create", required = true)
            @Valid @RequestBody SubjectRequestDTO requestDTO) {
        return new ResponseEntity<>(subjectService.create(surveyEditionId, requestDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get subject by ID",
            description = "Retrieves a specific subject's information"
    )
    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> getById(
            @Parameter(description = "ID of the subject to retrieve", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getById(id));
    }

    @Operation(
            summary = "Get subjects by survey edition",
            description = "Retrieves all subjects for a specific survey edition"
    )
    @GetMapping("/survey-edition/{surveyEditionId}")
    public ResponseEntity<List<SubjectResponseDTO>> getBySurveyEdition(
            @Parameter(description = "ID of the survey edition to get subjects for", example = "1")
            @PathVariable Long surveyEditionId) {
        return ResponseEntity.ok(subjectService.getBySurveyEditionId(surveyEditionId));
    }

    @Operation(
            summary = "Get child subjects",
            description = "Retrieves all child subjects under a specific parent subject"
    )
    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<SubjectResponseDTO>> getByParentId(
            @Parameter(description = "ID of the parent subject to get children for", example = "1")
            @PathVariable Long parentId) {
        return ResponseEntity.ok(subjectService.getByParentId(parentId));
    }

    @Operation(
            summary = "Update subject",
            description = "Updates an existing subject's information"
    )
    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> update(
            @Parameter(description = "ID of the subject to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated subject details", required = true)
            @Valid @RequestBody SubjectRequestDTO requestDTO) {
        return ResponseEntity.ok(subjectService.update(id, requestDTO));
    }

    @Operation(
            summary = "Delete subject",
            description = "Removes a subject and all its child subjects from the system"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the subject to delete", example = "1")
            @PathVariable Long id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}