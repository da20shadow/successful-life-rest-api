package com.successfulliferestapi.Task.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditChecklistItemDTO {
    private String title;
    private boolean completed;
}
