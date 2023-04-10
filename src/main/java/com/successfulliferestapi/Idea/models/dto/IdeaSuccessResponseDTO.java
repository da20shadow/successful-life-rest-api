package com.successfulliferestapi.Idea.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdeaSuccessResponseDTO {
    private String message;
    private IdeaDTO idea;
}
