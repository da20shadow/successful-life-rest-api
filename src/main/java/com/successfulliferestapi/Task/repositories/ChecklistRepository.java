package com.successfulliferestapi.Task.repositories;

import com.successfulliferestapi.Task.models.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistRepository extends JpaRepository<ChecklistItem,Long> {
    List<ChecklistItem> findAllByTaskIdAndUserId(Long taskId, Long userId);

    Optional<ChecklistItem> findByTitleAndTaskIdAndUserId(String title, Long taskId, Long userId);

    Optional<ChecklistItem> findByIdAndUserId(Long itemId, Long userId);

    List<ChecklistItem> findByTaskIdAndUserId(Long taskId, Long userId);
}
