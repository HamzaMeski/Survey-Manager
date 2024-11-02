package com.SurveyManager.BACKEND.entity;

import com.SurveyManager.BACKEND.util.constants.QuestionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String text;
    
    @Column(name = "answer_count")
    private Integer answerCount;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType type;
    
    private Boolean required = false;
    
    @Column(name = "order_index")
    private Integer orderIndex;
    
    @Column(columnDefinition = "TEXT")
    private String instructions;
    
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers;
} 