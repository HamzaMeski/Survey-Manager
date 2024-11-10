package com.SurveyManager.BACKEND.controller;

import com.SurveyManager.BACKEND.dto.request.AnswerRequestDTO;
import com.SurveyManager.BACKEND.dto.response.AnswerResponseDTO;
import com.SurveyManager.BACKEND.service.AnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
@Tag(name = "Answer", description = "Answer management APIs")
public class AnswerController {
    private final AnswerService answerService;

    @Operation(
            summary = "Create new answer",
            description = "Creates a new answer for a specific question"
    )
    @PostMapping("/question/{questionId}")
    public ResponseEntity<AnswerResponseDTO> create(
            @Parameter(description = "ID of the question to add answer to", example = "1")
            @PathVariable Long questionId,
            @Parameter(description = "Answer details", required = true)
            @Valid @RequestBody AnswerRequestDTO requestDTO) {
        return new ResponseEntity<>(answerService.create(questionId, requestDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get answer by ID",
            description = "Retrieves a specific answer by its ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<AnswerResponseDTO> getById(
            @Parameter(description = "ID of the answer to retrieve", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(answerService.getById(id));
    }

    @Operation(
            summary = "Get answers by question",
            description = "Retrieves all answers for a specific question"
    )
    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<AnswerResponseDTO>> getByQuestionId(
            @Parameter(description = "ID of the question to get answers for", example = "1")
            @PathVariable Long questionId) {
        return ResponseEntity.ok(answerService.getByQuestionId(questionId));
    }

    @Operation(
            summary = "Update answer",
            description = "Updates an existing answer's details"
    )
    @PutMapping("/{id}")
    public ResponseEntity<AnswerResponseDTO> update(
            @Parameter(description = "ID of the answer to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated answer details", required = true)
            @Valid @RequestBody AnswerRequestDTO requestDTO) {
        return ResponseEntity.ok(answerService.update(id, requestDTO));
    }

    @Operation(
            summary = "Delete answer",
            description = "Deletes an answer permanently"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the answer to delete", example = "1")
            @PathVariable Long id) {
        answerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Submit multiple choice answers",
            description = "Records multiple selections for a multi-choice question"
    )
    @PutMapping("/participate/{ids}/multiChoice")
    public ResponseEntity<Map<String, String>> participateMultiChoiceAnswer(
            @Parameter(
                    description = "Comma-separated answer IDs to select",
                    example = "1,2,3,4"
            )
            @PathVariable String ids) {
        List<Long> answersIDs = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        answerService.participateMultiChoiceAnswer(answersIDs);
        return ResponseEntity.ok(Map.of("message", "Multi-choice answers selections done successfully."));
    }

    @Operation(
            summary = "Submit single choice answer",
            description = "Records a single selection for a single-choice question"
    )
    @PutMapping("/participate/{id}/singleChoice")
    public ResponseEntity<Map<String, String>> participateSingleChoiceAnswer(
            @Parameter(
                    description = "ID of the answer to select",
                    example = "1"
            )
            @PathVariable Long id) {
        answerService.participateSingleChoiceAnswer(id);
        return ResponseEntity.ok(Map.of("message", "Single choice answer selections done successfully."));
    }
}