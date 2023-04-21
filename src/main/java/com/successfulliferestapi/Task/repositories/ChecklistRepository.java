package com.successfulliferestapi.Task.repositories;

import com.successfulliferestapi.Task.models.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistRepository extends JpaRepository<ChecklistItem,Long> {

    List<ChecklistItem> findAllByTaskIdAndUserIdAndDeletedFalse(Long taskId, Long userId);

    Optional<ChecklistItem> findByTitleAndTaskIdAndUserIdAndDeletedFalse(String title, Long taskId, Long userId);

    Optional<ChecklistItem> findByIdAndUserIdAndDeletedFalse(Long itemId, Long userId);

    List<ChecklistItem> findByTaskIdAndUserIdAndDeletedFalse(Long taskId, Long userId);

    //Change is deleted
//    @Modifying
//    @Query("UPDATE ChecklistItem c SET c.deleted = :isDeleted " +
//            "WHERE c.user.id = :userId AND c.task.target.goal.id = :goalId")
//    void changeChecklistItemsDeletedByUserIdAndGoalId(Long userId, Long goalId, boolean isDeleted);

    @Modifying
    @Query(value = "UPDATE checklist_items ch " +
            "JOIN tasks AS task ON ch.task_id = task.id " +
            "JOIN targets AS tar ON task.target_id = tar.id " +
            "JOIN goals AS g ON tar.goal_id = :goalId " +
            "SET ch.deleted = :isDeleted, ch.deleted_at = NOW() " +
            "WHERE task.user_id = :userId AND g.id = :goalId", nativeQuery = true)
    void changeChecklistItemsDeletedByUserIdAndGoalId(Long userId, Long goalId, boolean isDeleted);
}
