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
public class ChangeUsernameRequestDTO {

    @NotBlank(message = "Username can not be empty!")
    @Size(min = 5, max = 45, message = "Username must be between 5 and 45 characters long!")
    @Pattern(regexp = "^[a-z]+[a-z0-9_]+$",message = "Username can contains only letters, digits and underscore!")
    private String username;

}
