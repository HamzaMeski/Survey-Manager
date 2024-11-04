package com.SurveyManager.BACKEND.controller;

import com.SurveyManager.BACKEND.dto.request.SubjectRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SubjectResponseDTO;
import com.SurveyManager.BACKEND.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/survey-edition/{surveyEditionId}")
    public ResponseEntity<SubjectResponseDTO> create(
            @PathVariable Long surveyEditionId,
            @Valid @RequestBody SubjectRequestDTO requestDTO) {
        return new ResponseEntity<>(subjectService.create(surveyEditionId, requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(subjectService.getById(id));
    }

    @GetMapping("/survey-edition/{surveyEditionId}")
    public ResponseEntity<List<SubjectResponseDTO>> getBySurveyEdition(@PathVariable Long surveyEditionId) {
        return ResponseEntity.ok(subjectService.getBySurveyEditionId(surveyEditionId));
    }

    @GetMapping("/parent/{parentId}")
    public ResponseEntity<List<SubjectResponseDTO>> getByParentId(@PathVariable Long parentId) {
        return ResponseEntity.ok(subjectService.getByParentId(parentId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody SubjectRequestDTO requestDTO) {
        return ResponseEntity.ok(subjectService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 