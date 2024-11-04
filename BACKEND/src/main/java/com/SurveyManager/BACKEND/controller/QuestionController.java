package com.SurveyManager.BACKEND.controller;

import com.SurveyManager.BACKEND.dto.request.QuestionRequestDTO;
import com.SurveyManager.BACKEND.dto.response.QuestionResponseDTO;
import com.SurveyManager.BACKEND.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/subject/{subjectId}")
    public ResponseEntity<QuestionResponseDTO> create(
            @PathVariable Long subjectId,
            @Valid @RequestBody QuestionRequestDTO requestDTO) {
        return new ResponseEntity<>(questionService.create(subjectId, requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(questionService.getById(id));
    }

    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<QuestionResponseDTO>> getBySubjectId(@PathVariable Long subjectId) {
        return ResponseEntity.ok(questionService.getBySubjectId(subjectId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody QuestionRequestDTO requestDTO) {
        return ResponseEntity.ok(questionService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 