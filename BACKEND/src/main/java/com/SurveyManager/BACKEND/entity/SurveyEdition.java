package com.SurveyManager.BACKEND.entity;

import com.SurveyManager.BACKEND.util.constants.EditionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "survey_editions")
@Getter
@Setter
public class SurveyEdition {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDateTime creationDate;
    
    @Column(nullable = false)
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    @Column(nullable = false)
    private Integer year;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EditionStatus status;
    @PrePersist
    protected void onCreate() {
        status = EditionStatus.DRAFT;
    }
    
    private String version;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @OneToMany(mappedBy = "surveyEdition", cascade = CascadeType.ALL)
    List<Subject> subjects;
} 