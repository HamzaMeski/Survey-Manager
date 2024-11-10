package com.SurveyManager.BACKEND.controller;

import com.SurveyManager.BACKEND.dto.request.OwnerRequestDTO;
import com.SurveyManager.BACKEND.dto.response.OwnerResponseDTO;
import com.SurveyManager.BACKEND.service.OwnerService;
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
@RequestMapping("/api/owners")
@RequiredArgsConstructor
@Tag(name = "Owner", description = "Owner management APIs")
public class OwnerController {

    private final OwnerService ownerService;

    @Operation(
            summary = "Create new owner",
            description = "Creates a new owner with the provided details"
    )
    @PostMapping
    public ResponseEntity<OwnerResponseDTO> create(
            @Parameter(description = "Owner details to create", required = true)
            @Valid @RequestBody OwnerRequestDTO requestDTO) {
        return new ResponseEntity<>(ownerService.create(requestDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get owner by ID",
            description = "Retrieves an owner's information by their ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponseDTO> getById(
            @Parameter(description = "ID of the owner to retrieve", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(ownerService.getById(id));
    }

    @Operation(
            summary = "Get all owners",
            description = "Retrieves a list of all owners in the system"
    )
    @GetMapping
    public ResponseEntity<List<OwnerResponseDTO>> getAll() {
        return ResponseEntity.ok(ownerService.getAll());
    }

    @Operation(
            summary = "Update owner",
            description = "Updates an existing owner's information"
    )
    @PutMapping("/{id}")
    public ResponseEntity<OwnerResponseDTO> update(
            @Parameter(description = "ID of the owner to update", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Updated owner details", required = true)
            @Valid @RequestBody OwnerRequestDTO requestDTO) {
        return ResponseEntity.ok(ownerService.update(id, requestDTO));
    }

    @Operation(
            summary = "Delete owner",
            description = "Removes an owner from the system"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the owner to delete", example = "1")
            @PathVariable Long id) {
        ownerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}