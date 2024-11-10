package com.SurveyManager.BACKEND.controller;

import com.SurveyManager.BACKEND.dto.request.AnswerRequestDTO;
import com.SurveyManager.BACKEND.dto.response.AnswerResponseDTO;
import com.SurveyManager.BACKEND.service.AnswerService;
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
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/question/{questionId}")
    public ResponseEntity<AnswerResponseDTO> create(
            @PathVariable Long questionId,
            @Valid @RequestBody AnswerRequestDTO requestDTO) {
        return new ResponseEntity<>(answerService.create(questionId, requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnswerResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(answerService.getById(id));
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<AnswerResponseDTO>> getByQuestionId(@PathVariable Long questionId) {
        return ResponseEntity.ok(answerService.getByQuestionId(questionId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnswerResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AnswerRequestDTO requestDTO) {
        return ResponseEntity.ok(answerService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        answerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/participate/{ids}/multiChoice")
    public ResponseEntity<Map<String, String>> participateMultiChoiceAnswer(
            @PathVariable String ids
    ) {
        List<Long> answersIDs = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        answerService.participateMultiChoiceAnswer(answersIDs);
        return ResponseEntity.ok(Map.of("message", "Multi-choice answers selections done successfully."));
    }

    @PutMapping("/participate/{id}/singleChoice")
    public ResponseEntity<Map<String, String>> participateSingleChoiceAnswer(
            @PathVariable Long id
    ) {
        answerService.participateSingleChoiceAnswer(id);
        return ResponseEntity.ok(Map.of("message", "Single choice answer selections done successfully."));
    }
} 