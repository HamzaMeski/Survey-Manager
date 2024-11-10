package com.SurveyManager.BACKEND.mapper;

import com.SurveyManager.BACKEND.dto.response.full.SurveyFullResponseDTO;
import com.SurveyManager.BACKEND.dto.response.full.EditionFullDTO;
import com.SurveyManager.BACKEND.dto.response.full.SubjectFullDTO;
import com.SurveyManager.BACKEND.dto.response.full.QuestionFullDTO;
import com.SurveyManager.BACKEND.entity.Survey;
import com.SurveyManager.BACKEND.entity.SurveyEdition;
import com.SurveyManager.BACKEND.entity.Subject;
import com.SurveyManager.BACKEND.entity.Question;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OwnerMapper.class})
public interface SurveyFullMapper {
    
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "editions", source = "editions")
    SurveyFullResponseDTO toFullResponseDTO(Survey survey);

    @Mapping(target = "subjects", source = "subjects")
    EditionFullDTO toEditionFullDTO(SurveyEdition edition);

    @Mapping(target = "questions", source = "questions")
    @Mapping(target = "subSubjects", source = "subSubjects")
    SubjectFullDTO toSubjectFullDTO(Subject subject);

    @Mapping(target = "answers", source = "answers")
    QuestionFullDTO toQuestionFullDTO(Question question);
} 