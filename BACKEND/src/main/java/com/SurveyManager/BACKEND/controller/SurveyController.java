package com.SurveyManager.BACKEND.controller;

import com.SurveyManager.BACKEND.dto.request.SurveyRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SurveyResponseDTO;
import com.SurveyManager.BACKEND.service.SurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/surveys")
@RequiredArgsConstructor
public class SurveyController {
    
    private final SurveyService surveyService;
    
    @PostMapping
    public ResponseEntity<SurveyResponseDTO> create(@Valid @RequestBody SurveyRequestDTO requestDTO) {
        return new ResponseEntity<>(surveyService.create(requestDTO), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SurveyResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(surveyService.getById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<SurveyResponseDTO>> getAll() {
        return ResponseEntity.ok(surveyService.getAll());
    }
    
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<SurveyResponseDTO>> getByOwnerId(@PathVariable Long ownerId) {
        return ResponseEntity.ok(surveyService.getByOwnerId(ownerId));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SurveyResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody SurveyRequestDTO requestDTO) {
        return ResponseEntity.ok(surveyService.update(id, requestDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        surveyService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 