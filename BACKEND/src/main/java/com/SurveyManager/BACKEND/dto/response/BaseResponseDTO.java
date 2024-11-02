package com.SurveyManager.BACKEND.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BaseResponseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 