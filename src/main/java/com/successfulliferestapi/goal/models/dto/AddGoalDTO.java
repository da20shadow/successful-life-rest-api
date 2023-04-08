package com.successfulliferestapi.Goal.models.dto;

import com.successfulliferestapi.Goal.models.enums.GoalCategory;
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

    @NotNull(message = "Title can not be empty!")
    @Size(min = 5, max = 145)
    private String title;

    @NotNull(message = "Description can not be empty!")
    @Size(min = 5, message = "Description must be at least 5 characters long!")
    private String description;

    @NotNull(message = "Deadline can not be empty!")
    private String deadline;

    @NotNull(message = "Category can not be empty!")
    private GoalCategory category;

}
