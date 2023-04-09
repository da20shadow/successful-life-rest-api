package com.successfulliferestapi.Target.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddTargetSuccessResponseDTO {
    private String message;
    private TargetDTO target;
}
