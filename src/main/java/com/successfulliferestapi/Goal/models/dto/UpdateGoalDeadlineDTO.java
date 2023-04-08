package com.successfulliferestapi.Goal.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGoalDeadlineDTO {
    @NotBlank(message = "Deadline can not be empty!")
    private String deadline;
}
