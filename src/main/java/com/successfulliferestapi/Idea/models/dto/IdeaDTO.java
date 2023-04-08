package com.successfulliferestapi.Idea.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdeaDTO {
    private Long id;
    private String title;
    private String description;
    private Set<String> tags;
    private Long goalId = null;

}
