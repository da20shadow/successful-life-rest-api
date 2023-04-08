package com.successfulliferestapi.Task.models.entity;

import com.successfulliferestapi.Shared.models.entity.BaseEntity;
import com.successfulliferestapi.User.models.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "checklist_items", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"task_id", "title"})
})
public class ChecklistItem extends BaseEntity {

    @Column(name = "title", length = 145)
    private String title;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean completed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
