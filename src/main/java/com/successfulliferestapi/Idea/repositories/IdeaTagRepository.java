package com.successfulliferestapi.Idea.repositories;

import com.successfulliferestapi.Idea.models.entity.IdeaTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaTagRepository extends JpaRepository<IdeaTag, Long> {
    IdeaTag findByName(String newTagName);
}
