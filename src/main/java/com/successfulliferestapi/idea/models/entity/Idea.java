package com.successfulliferestapi.Idea.models.entity;

import com.successfulliferestapi.Goal.models.entity.Goal;
import com.successfulliferestapi.Shared.models.entity.BaseEntity;
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
@Table(name = "ideas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "title"})
})
public class Idea extends BaseEntity {

    @Column(name = "title",nullable = false,length = 145)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "deleted", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "idea_tag",
            joinColumns = @JoinColumn(name = "idea_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<IdeaTag> tags = new HashSet<>();

    /** addTag method checks whether a tag with the same name already exists in the tags set.
     * If it does, the existing tag is used instead of creating a new one.
     * Otherwise, a new tag is created and added to the set.
     * With these modifications, users can add tags to their Ideas using the same name as existing tags,
     * and the tags will be shared between users.
     * */
    public void addTag(IdeaTag tag) {
        IdeaTag existingTag = tags.stream()
                .filter(t -> t.getName().equals(tag.getName()))
                .findFirst()
                .orElse(null);
        if (existingTag != null) {
            tags.add(existingTag);
        } else {
            tags.add(tag);
        }
    }
}
