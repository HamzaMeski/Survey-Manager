package com.SurveyManager.BACKEND.mapper;

import com.SurveyManager.BACKEND.dto.request.SurveyEditionRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SurveyEditionResponseDTO;
import com.SurveyManager.BACKEND.entity.SurveyEdition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface SurveyEditionMapper {
    
    @Mapping(target = "surveyId", source = "survey.id")
    SurveyEditionResponseDTO toResponseDTO(SurveyEdition surveyEdition);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "survey.id", source = "surveyId")
    @Mapping(target = "subjects", ignore = true)
    SurveyEdition toEntity(SurveyEditionRequestDTO requestDTO);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "survey", ignore = true)
    @Mapping(target = "subjects", ignore = true)
    void updateEntity(@MappingTarget SurveyEdition surveyEdition, SurveyEditionRequestDTO requestDTO);
} 