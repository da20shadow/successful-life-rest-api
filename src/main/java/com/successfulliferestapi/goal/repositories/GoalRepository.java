package com.successfulliferestapi.Goal.repositories;

import com.successfulliferestapi.Goal.models.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalRepository extends JpaRepository<Goal,Long> {
}
