package com.successfulliferestapi.idea.repositories;

import com.successfulliferestapi.idea.models.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaRepository extends JpaRepository<Idea,Long> {
}
