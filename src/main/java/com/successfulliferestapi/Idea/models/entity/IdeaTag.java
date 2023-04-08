package com.successfulliferestapi.Idea.models.entity;

import com.successfulliferestapi.Shared.models.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "idea_tags")
public class IdeaTag extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true, length = 75)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Idea> ideas = new HashSet<>();

    public IdeaTag(String name) {
        this.name = name.toLowerCase();
    }

    public IdeaTag setName(String name) {
        this.name = name.toLowerCase();
        return this;
    }
}
