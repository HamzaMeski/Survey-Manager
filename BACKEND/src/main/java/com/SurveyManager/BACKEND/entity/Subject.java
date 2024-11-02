package com.SurveyManager.BACKEND.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject extends BaseEntity {
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Subject parentSubject;
    
    @OneToMany(mappedBy = "parentSubject")
    private List<Subject> subSubjects;
    
    @Column(nullable = false)
    private Integer level;
    
    @Column(nullable = false)
    private String path;
} 