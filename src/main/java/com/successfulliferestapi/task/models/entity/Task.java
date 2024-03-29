package com.successfulliferestapi.Task.models.entity;

import com.successfulliferestapi.Shared.models.entity.BaseEntity;
import com.successfulliferestapi.Target.models.entity.Target;
import com.successfulliferestapi.Task.models.enums.TaskPriority;
import com.successfulliferestapi.Task.models.enums.TaskStatus;
import com.successfulliferestapi.User.models.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"target_id", "title"})
})
public class Task extends BaseEntity {

    @Column(name = "title", nullable = false, length = 145)
    private String title;

    @Column(name = "description", length = 5000)
    private String description;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.TO_DO; // Set default enum value for status

    @Builder.Default
    @Enumerated(EnumType.ORDINAL)
    private TaskPriority priority = TaskPriority.NO_PRIORITY; // Set default enum value for priority

    @Builder.Default
    @Column(name = "urgent", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean urgent = false;

    @Builder.Default
    @Column(name = "important", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean important = false;

    @Builder.Default
    @Column(name = "favorite", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean favorite = false;

    @Builder.Default
    @Column(name = "deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Builder.Default
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChecklistItem> checklist = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id")
    private Target target;
}
