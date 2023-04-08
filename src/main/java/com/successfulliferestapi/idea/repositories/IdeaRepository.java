package com.successfulliferestapi.Idea.repositories;

import com.successfulliferestapi.Idea.models.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaRepository extends JpaRepository<Idea,Long> {
}
