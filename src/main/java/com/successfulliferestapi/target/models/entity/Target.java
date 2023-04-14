package com.successfulliferestapi.Target.models.entity;

import com.successfulliferestapi.Goal.models.entity.Goal;
import com.successfulliferestapi.Shared.models.entity.BaseEntity;
import com.successfulliferestapi.Task.models.entity.Task;
import com.successfulliferestapi.User.models.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "targets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"goal_id", "title"})
})
public class Target extends BaseEntity {

    @Column(name = "title",nullable = false,length = 145)
    private String title;

    @Column(name = "description",columnDefinition = "TEXT",nullable = false)
    private String description;

    @Builder.Default
    @Column(name = "favorite",columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean favorite = false;

    @Builder.Default
    @Column(name = "deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id",nullable = false)
    private Goal goal;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "target", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

}
