package com.SurveyManager.BACKEND.mapper;

import com.SurveyManager.BACKEND.dto.request.SurveyRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SurveyResponseDTO;
import com.SurveyManager.BACKEND.entity.Survey;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {OwnerMapper.class})
public interface SurveyMapper {
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "editions", source = "editions")
    SurveyResponseDTO toResponseDTO(Survey survey);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "owner.id", source = "ownerId")
    @Mapping(target = "editions", ignore = true)
    Survey toEntity(SurveyRequestDTO requestDTO);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "editions", ignore = true)
    void updateEntity(@MappingTarget Survey survey, SurveyRequestDTO requestDTO);
} 