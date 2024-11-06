package com.SurveyManager.BACKEND.entity;

import com.SurveyManager.BACKEND.util.constants.OwnerRole;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "owners")
@Getter
@Setter
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
    
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Survey> surveys;
} 