package com.successfulliferestapi.Target.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTargetTitleDTO {
    @NotBlank(message = "Title can not be empty!")
    @Size(min = 5, max = 145, message = "Title must be between 5 and 145 characters long.")
    private String title;
}
