package com.SurveyManager.BACKEND.mapper;

import com.SurveyManager.BACKEND.dto.request.SubjectRequestDTO;
import com.SurveyManager.BACKEND.dto.response.SubjectResponseDTO;
import com.SurveyManager.BACKEND.entity.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "path", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "parentSubject", ignore = true)
    @Mapping(target = "subSubjects", ignore = true)
    @Mapping(target = "surveyEdition", ignore = true)
    @Mapping(target = "questions", ignore = true)
    Subject toEntity(SubjectRequestDTO requestDTO);

    @Mapping(target = "parentSubjectId", source = "parentSubject.id")
    @Mapping(target = "subSubjects", source = "subSubjects")
    SubjectResponseDTO toResponseDTO(Subject subject);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "path", ignore = true)
    @Mapping(target = "level", ignore = true)
    @Mapping(target = "parentSubject", ignore = true)
    @Mapping(target = "subSubjects", ignore = true)
    @Mapping(target = "surveyEdition", ignore = true)
    @Mapping(target = "questions", ignore = true)
    void updateEntity(@MappingTarget Subject subject, SubjectRequestDTO requestDTO);
}