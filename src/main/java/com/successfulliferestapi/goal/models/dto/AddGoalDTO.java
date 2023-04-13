package com.successfulliferestapi.Goal.models.dto;

import com.successfulliferestapi.Goal.models.enums.GoalCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddGoalDTO {

    @NotBlank(message = "Title can not be empty!")
    @Size(min = 5, max = 145)
    private String title;

    @NotBlank(message = "Description can not be empty!")
    @Size(min = 5, max = 5000, message = "Description must be between 5 - 5000 characters long.")
    private String description;

    @NotBlank(message = "Deadline can not be empty!")
    private String deadline;

    @NotNull(message = "Category can not be empty!")
    private GoalCategory category;

}
