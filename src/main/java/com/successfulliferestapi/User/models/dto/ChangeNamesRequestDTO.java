package com.successfulliferestapi.User.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeNamesRequestDTO {

    @NotBlank(message = "First name is required!")
    @Size(min = 2, max = 45, message = "First name must be between 2 and 45 characters long!")
    @Pattern(regexp = "^[A-Z][a-z]+$",message = "First name must contains only letters!")
    private String firstName;

    @NotBlank(message = "Last name is required!")
    @Size(min = 1, max = 45, message = "Last name must be between 1 and 45 characters long!")
    @Pattern(regexp = "^[a-zA-Z]+$",message = "Last name must contains only letters!")
    private String lastName;

}
