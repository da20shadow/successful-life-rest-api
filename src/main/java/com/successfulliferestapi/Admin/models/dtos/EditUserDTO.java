package com.successfulliferestapi.Admin.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDTO {

    @Size(min = 2, max = 45,message = "First name must be between 2-45 characters long!")
    @Pattern(regexp = "^[a-z-A-Z]+$",message = "First name can contains only letters!")
    private String firstName;

    @Size(min = 2, max = 45,message = "Last name must be between 2-45 characters long!")
    @Pattern(regexp = "^[a-z-A-Z]+$",message = "Last name can contains only letters!")
    private String lastName;

    @Size(min = 9, max = 145,message = "Email must be between 9-145 characters long!")
    @Pattern(regexp = "^[a-z]+[a-z0-9_]+[@][a-z]{2,}[.][a-z]{2,}$",message = "Invalid Email format!")
    private String email;

    @Size(min = 2, max = 45,message = "Username must be between 2-45 characters long!")
    @Pattern(regexp = "^[a-z0-9_]+$",message = "Username must contains only letters digits and underscore!")
    private String username;

    @Pattern(regexp = "^[A-Z]+$",message = "Invalid User ROLE!")
    private String role;

}
