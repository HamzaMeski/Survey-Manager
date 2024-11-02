package com.SurveyManager.BACKEND.dto.response;

import com.SurveyManager.BACKEND.util.constants.OwnerRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OwnerResponseDTO extends BaseResponseDTO {
    private String name;
    private String email;
    private OwnerRole role;
    private Boolean active;
}