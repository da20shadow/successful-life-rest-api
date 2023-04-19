package com.successfulliferestapi.Idea.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AddIdeaDTO {

    @NotBlank(message = "Idea title is required")
    @Size(min = 5, max = 145,message = "Title must be between 5 - 145 characters long!")
    private String title;

    @Size(min = 5,message = "Description must be at least 5 characters long!")
    private String description;

    private Set<String> tags;

    private Long goalId = null;
}
