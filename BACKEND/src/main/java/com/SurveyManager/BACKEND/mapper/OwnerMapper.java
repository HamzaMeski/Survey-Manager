package com.SurveyManager.BACKEND.mapper;

import com.SurveyManager.BACKEND.dto.request.OwnerRequestDTO;
import com.SurveyManager.BACKEND.dto.response.OwnerResponseDTO;
import com.SurveyManager.BACKEND.entity.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OwnerMapper {
    OwnerResponseDTO toResponseDTO(Owner owner);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "surveys", ignore = true)
    Owner toEntity(OwnerRequestDTO requestDTO);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "surveys", ignore = true)
    void updateEntity(@MappingTarget Owner owner, OwnerRequestDTO requestDTO);
} 