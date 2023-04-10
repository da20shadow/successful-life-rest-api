package com.successfulliferestapi.Idea.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditIdeaDTO {
    private String title;
    private String description;
    private Set<String> tags;

}
