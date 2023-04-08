package com.successfulliferestapi.Goal.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGoalSuccessResponseDTO {
    private String message;
    private GoalDTO goal;
}
