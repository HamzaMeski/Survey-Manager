package com.SurveyManager.BACKEND.mapper;

import com.SurveyManager.BACKEND.dto.request.QuestionRequestDTO;
import com.SurveyManager.BACKEND.dto.response.QuestionResponseDTO;
import com.SurveyManager.BACKEND.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "answerCount", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "answers", ignore = true)
    Question toEntity(QuestionRequestDTO requestDTO);

    //@Mapping(target = "answers", ignore = true)
    QuestionResponseDTO toResponseDTO(Question question);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "answerCount", ignore = true)
    @Mapping(target = "subject", ignore = true)
    @Mapping(target = "answers", ignore = true)
    void updateEntity(@MappingTarget Question question, QuestionRequestDTO requestDTO);
}