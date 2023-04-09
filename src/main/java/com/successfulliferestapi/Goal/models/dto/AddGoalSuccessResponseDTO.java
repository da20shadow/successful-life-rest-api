package com.successfulliferestapi.Goal.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddGoalSuccessResponseDTO {
    private String message;
    private GoalDTO goal;
}
