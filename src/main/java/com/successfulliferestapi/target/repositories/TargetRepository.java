package com.successfulliferestapi.Target.repositories;

import com.successfulliferestapi.Target.models.entity.Target;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TargetRepository extends JpaRepository<Target,Long> {
    Optional<Target> findByTitleAndGoalIdAndUserId(String title, Long goalId, Long userId);

    Optional<Target> findByIdAndUserId(Long targetId, Long userId);

    //Retrieve all TARGETS by GOAL ID Pageable
    Page<Target> findAllByGoalIdAndUserId(Long goalId, Long userId, Pageable pageable);

    //Retrieve all TARGETS by GOAL ID Set
    List<Target> findAllByGoalIdAndUserId(Long goalId, Long userId);
}
