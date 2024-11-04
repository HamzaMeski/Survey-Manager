package com.SurveyManager.BACKEND.mapper;

import com.SurveyManager.BACKEND.dto.request.AnswerRequestDTO;
import com.SurveyManager.BACKEND.dto.response.AnswerResponseDTO;
import com.SurveyManager.BACKEND.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "selectionCount", ignore = true)
    @Mapping(target = "question", ignore = true)
    Answer toEntity(AnswerRequestDTO requestDTO);

    @Mapping(target = "questionId", source = "question.id")
    AnswerResponseDTO toResponseDTO(Answer answer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "selectionCount", ignore = true)
    @Mapping(target = "question", ignore = true)
    void updateEntity(@MappingTarget Answer answer, AnswerRequestDTO requestDTO);
} 