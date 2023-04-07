package com.successfulliferestapi.goal.repositories;

import com.successfulliferestapi.goal.models.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal,Long> {
}
