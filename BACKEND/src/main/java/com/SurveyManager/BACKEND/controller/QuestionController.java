package com.SurveyManager.BACKEND.controller;

import com.SurveyManager.BACKEND.dto.request.QuestionRequestDTO;
import com.SurveyManager.BACKEND.dto.response.QuestionResponseDTO;
import com.SurveyManager.BACKEND.service.QuestionService;
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
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@Tag(name = "Question", description = "Question management APIs")
public class QuestionController {

    private final QuestionService questionService;

    @Operation(
            summary = "Create new question",
            description = "Creates a new question for a specific subject"
    )
    @PostMapping("/subject/{subjectId}")
    public ResponseEntity<QuestionResponseDTO> create(
            @Parameter(description = "ID of the subject to add question to", example = "1")
            @PathVariable Long subjectId,
            @Parameter(description = "Question details to create", required = true)
            @Valid @RequestBody QuestionRequestDTO requestDTO) {
        return new ResponseEntity<>(questionService.create(subjectId, requestDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get question by ID",
            description = "Retrieves a specific question's information"
    )
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> getById(
            @Parameter(description = "ID of the question to retrieve", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(questionService.getById(id));
    }

    @Operation(
            summary = "Get questions by subject",
            description = "Retrieves all questions for a specific subject"
    )
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<QuestionResponseDTO>> getBySubjectId(
            @Parameter(description = "ID of the subject to get questions for", example = "1")
            @PathVariable Long subjectId) {
        return ResponseEntity.ok(questionService.getBySubjectId(subjectId));
    }

    @Operation(
            summary = "Update question",
            description = "Updates an existing question's information"
    )
    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> update(
            @Parameter(description = "ID of the question to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated question details", required = true)
            @Valid @RequestBody QuestionRequestDTO requestDTO) {
        return ResponseEntity.ok(questionService.update(id, requestDTO));
    }

    @Operation(
            summary = "Delete question",
            description = "Removes a question and its associated answers from the system"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the question to delete", example = "1")
            @PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}