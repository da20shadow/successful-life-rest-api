package com.successfulliferestapi.Task.repositories;

import com.successfulliferestapi.Task.models.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {

    // Retrieve count of tasks by targetId
    int countByTargetId(Long targetId);

    // Retrieve count of completed tasks by targetId
    @Query("SELECT COUNT(t) FROM Task t WHERE t.target.id = :targetId AND t.status = 'COMPLETED'")
    int countCompletedByTargetId(Long targetId);

    // Retrieve completed tasks by targetId
    @Query("SELECT t FROM Task t WHERE t.target.id = :targetId AND t.status = 'COMPLETED'")
    Page<Task> findCompletedByTargetId(Long targetId, Pageable pageable);

    Optional<Task> findByTitleAndTargetIdAndUserId(String title, Long targetId, Long userId);

    Page<Task> findByUserIdAndTargetId(Long userId, Long targetId, Pageable pageable);

    Optional<Task> findByIdAndUserId(Long taskId, Long userId);
}
