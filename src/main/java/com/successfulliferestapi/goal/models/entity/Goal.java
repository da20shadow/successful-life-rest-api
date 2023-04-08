package com.successfulliferestapi.Goal.models.entity;

import com.successfulliferestapi.Goal.models.enums.GoalCategory;
import com.successfulliferestapi.Idea.models.entity.Idea;
import com.successfulliferestapi.Shared.models.entity.BaseEntity;
import com.successfulliferestapi.Target.models.entity.Target;
import com.successfulliferestapi.User.models.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "goals", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "title"})
})
public class Goal extends BaseEntity {

    @Column(name = "title", nullable = false, length = 145)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private GoalCategory category;

    @Column(name = "favorite", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean favorite = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deadline")
    private LocalDate deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Idea> ideas = new HashSet<>();

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Target> targets = new HashSet<>();

    @Transient
    private int totalTargets;

    @Transient
    private int totalCompletedTargets;

    public Goal(String title, String description, GoalCategory category, LocalDate deadline, User user) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.createdAt = LocalDateTime.now(); // Set the creation date to the current date
        this.deadline = deadline;
        this.user = user;
    }

    public void addTarget(Target target) {
        targets.add(target);
        target.setGoal(this);
    }

}
