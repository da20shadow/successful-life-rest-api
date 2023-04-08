package com.successfulliferestapi.Target.models.dto;

import com.successfulliferestapi.Task.models.entity.Task;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TargetDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private int totalTasks;
    private int totalCompletedTasks;
    private List<Task> tasks;
}
