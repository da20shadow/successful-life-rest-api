package com.successfulliferestapi.Task.models.dto;

import com.successfulliferestapi.Task.models.enums.TaskPriority;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private Long id;

    @NotBlank
    private String title;

    private String description;

    private String status;

    private TaskPriority priority;

    private boolean urgent = false;

    private boolean important = false;

    private LocalDateTime createdAt;

    private LocalDateTime startDate;

    private LocalDateTime dueDate;

    private Long targetId;
}
