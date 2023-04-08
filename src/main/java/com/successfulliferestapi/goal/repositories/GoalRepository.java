package com.successfulliferestapi.Goal.repositories;

import com.successfulliferestapi.Goal.models.dto.GoalDTO;
import com.successfulliferestapi.Goal.models.entity.Goal;
import com.successfulliferestapi.Goal.models.enums.GoalCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal,Long> {
    boolean existsByTitleAndUser_Id(String title, Long userId);

    Optional<Goal> findByIdAndUserId(Long goalId, Long userId);

    Page<Goal> findByUserId(Long userId, Pageable pageable);

    Page<Goal> findByUserIdAndCategory(Long userId, GoalCategory goalCategory, Pageable pageable);
}
