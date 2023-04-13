package com.successfulliferestapi.Target.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddTargetDTO {

    @NotNull(message = "Title can not be empty!")
    @Size(min = 5, max = 145,message = "Title must be between 5 - 145 characters long!")
    private String title;

    @NotNull(message = "Description can not be empty!")
    @Size(min = 5, max = 5000, message = "Description must be between 5 - 5000 characters long.")
    private String description;

    @NotNull(message = "Goal ID can not be empty!")
    @Positive
    private Long goalId;

}
