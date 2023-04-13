package com.successfulliferestapi.Goal.models.dto;

import com.successfulliferestapi.Goal.models.enums.GoalCategory;
import com.successfulliferestapi.Target.models.dto.TargetDTO;
import com.successfulliferestapi.User.models.dto.UserDTO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoalDTO {
    private Long id;
    private String title;
    private String description;
    private GoalCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDate deadline;
    private int totalTargets;
    private int totalCompletedTargets;
    private int totalIdeas;
    private Set<TargetDTO> targets;
}
