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
public class UpdateTargetDescriptionDTO {
    @NotBlank(message = "Description can not be empty!")
    @Size(min = 5, message = "Description must be at least 5 characters long.")
    private String description;
}
