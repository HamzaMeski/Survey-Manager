package com.SurveyManager.BACKEND.controller;

import com.SurveyManager.BACKEND.dto.request.OwnerRequestDTO;
import com.SurveyManager.BACKEND.dto.response.OwnerResponseDTO;
import com.SurveyManager.BACKEND.service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class OwnerController {
    
    private final OwnerService ownerService;
    
    @PostMapping
    public ResponseEntity<OwnerResponseDTO> create(@Valid @RequestBody OwnerRequestDTO requestDTO) {
        return new ResponseEntity<>(ownerService.create(requestDTO), HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ownerService.getById(id));
    }
    
    @GetMapping
    public ResponseEntity<List<OwnerResponseDTO>> getAll() {
        return ResponseEntity.ok(ownerService.getAll());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody OwnerRequestDTO requestDTO) {
        return ResponseEntity.ok(ownerService.update(id, requestDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ownerService.delete(id);
        return ResponseEntity.noContent().build();
    }
} 