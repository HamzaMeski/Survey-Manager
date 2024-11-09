package com.SurveyManager.BACKEND.repository;

import com.SurveyManager.BACKEND.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findBySurveyEditionIdAndParentSubjectIsNull(Long surveyEditionId);
    List<Subject> findByParentSubjectId(Long parentId);
    boolean existsByTitleAndSurveyEditionId(String title, Long surveyEditionId);

    @Query("SELECT COUNT(s) FROM Subject s WHERE " +
            "((:parentPath = '') AND s.parentSubject IS NULL) OR " +
            "((:parentPath != '') AND s.parentSubject.path = :parentPath) " +
            "AND s.level = :level")
    long countByParentPathAndLevel(@Param("parentPath") String parentPath, @Param("level") Integer level);

    @Query("SELECT DISTINCT s FROM Subject s LEFT JOIN FETCH s.questions " +
            "WHERE s.surveyEdition.id IN :editionIds")
    List<Subject> findByEditionIdsWithQuestions(@Param("editionIds") List<Long> editionIds);
}