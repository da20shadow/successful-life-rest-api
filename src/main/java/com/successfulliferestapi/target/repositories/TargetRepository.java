package com.successfulliferestapi.Target.repositories;

import com.successfulliferestapi.Target.models.entity.Target;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TargetRepository extends JpaRepository<Target,Long> {
    Optional<Target> findByTitleAndGoalIdAndUserIdAndDeletedFalse(String title, Long goalId, Long userId);

    Optional<Target> findByIdAndUserIdAndDeletedFalse(Long targetId, Long userId);

    //Retrieve all TARGETS by GOAL ID Pageable
    Page<Target> findAllByGoalIdAndUserIdAndDeletedFalse(Long goalId, Long userId, Pageable pageable);

    //Retrieve all TARGETS by GOAL ID Set
    List<Target> findAllByGoalIdAndUserIdAndDeletedFalse(Long goalId, Long userId);

    //Change is deleted
    @Modifying
    @Query("UPDATE Target t SET t.deleted = :isDeleted, t.deletedAt = :now " +
            "WHERE t.user.id = :userId AND t.goal.id = :goalId")
    void changeTargetsDeletedByUserIdAndGoalId(Long userId, Long goalId, boolean isDeleted, LocalDateTime now);
}
