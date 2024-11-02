package com.SurveyManager.BACKEND.entity;

import com.SurveyManager.BACKEND.util.constants.OwnerRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "owners")
public class Owner extends BaseEntity {
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OwnerRole role;
    
    private Boolean active = true;
    
    @OneToMany(mappedBy = "owner")
    private List<Survey> surveys;
} 