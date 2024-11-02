package com.SurveyManager.BACKEND.entity;

import com.SurveyManager.BACKEND.util.constants.SurveyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "surveys")
public class Survey extends BaseEntity {
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SurveyStatus status;
    
    private String category;
    
    @Column(name = "target_audience")
    private String targetAudience;
    
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<SurveyEdition> editions;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;
} 