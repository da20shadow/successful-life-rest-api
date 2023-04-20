package com.successfulliferestapi.Goal.repositories;

import com.successfulliferestapi.Goal.models.dto.GoalDTO;
import com.successfulliferestapi.Goal.models.entity.Goal;
import com.successfulliferestapi.Goal.models.enums.GoalCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepository extends JpaRepository<Goal,Long> {
    boolean existsByTitleAndUser_Id(String title, Long userId);

    Optional<Goal> findByIdAndUserId(Long goalId, Long userId);

    Page<Goal> findByUserIdAndDeletedFalse(Long userId, Pageable pageable);

    Page<Goal> findByUserIdAndCategoryAndDeletedFalse(Long userId, GoalCategory goalCategory, Pageable pageable);

    //Get All Deleted goals
    @Query("SELECT g FROM Goal g WHERE g.user.id = :userId AND g.deleted = true")
    Page<Goal> findByUserIdAndDeletedTrue(Long userId, Pageable pageable);

    //Get All Deleted goals By Category
    Page<Goal> findByUserIdAndCategoryAndDeletedTrue(Long userId, GoalCategory goalCategory, Pageable pageable);

    //Delete goals in Trash older than 30 days!
    //TODO: remove it THIS NOT WORKS BECAUSE cascade not works for custom queries!
//    @Modifying
//    @Query("DELETE FROM Goal g WHERE g.deleted = true AND g.deletedAt < :thirtyDaysAgo")
//    void deleteExpiredTrashGoals(LocalDateTime thirtyDaysAgo);

    //GET All Delete goals in Trash older than 30 days!
    @Query("SELECT g FROM Goal g WHERE g.deleted = true AND g.deletedAt < :thirtyDaysAgo")
    List<Goal> getAllDeletedExpiredTrashGoals(LocalDateTime thirtyDaysAgo);

}
